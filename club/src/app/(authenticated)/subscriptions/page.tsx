'use client'

import {withAuth} from '@/components/withAuth'
import {useState, useEffect, useCallback} from 'react'
import {RegisterPartnerModal} from '@/components/RegisterPartnerModal'
import useApi from '@/hooks/useApi'
import {Button} from "@/components/ui/button"
import {Input} from "@/components/ui/input"
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import {ChevronDown, ChevronUp, ChevronsUpDown} from 'lucide-react'
import Link from "next/link";

interface Partner {
    idPartner: number;
    idUserPartner: {
        idPerson: {
            documentPerson: number;
            namePerson: string;
            cellphonePerson: number;
        };
        userName: string;
        roleUser: string;
    };
    amountPartner: number;
    typePartner: string;
    creationDatePartner: string;
}

interface ApiResponse {
    result: Partner[];
    statusCode: number;
    timestamp: string;
}

function Partners() {
    const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false)
    const [partners, setPartners] = useState<Partner[]>([])
    const [searchTerm, setSearchTerm] = useState('')
    const [sortColumn, setSortColumn] = useState<keyof Partner | ''>('')
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc')
    const {request, loading: apiLoading, error: apiError} = useApi<ApiResponse>()

    const loadPartners = useCallback(async () => {
        try {
            const response = await request('get', '/partnersAPI/v1/partner?typePartner=PENDIENTE')
            if (response && Array.isArray(response.body)) {
                setPartners(response.body)
            }
        } catch (error) {
            console.error('Error al cargar los socios:', error)
        }
    }, [request])

    useEffect(() => {
        loadPartners()
    }, [loadPartners])

    const handleRegisterPartner = async () => {
        await loadPartners()
    }

    const filteredPartners = partners.filter(partner =>
        partner.idUserPartner.idPerson.namePerson.toLowerCase().includes(searchTerm.toLowerCase()) ||
        partner.idUserPartner.userName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        partner.idUserPartner.idPerson.documentPerson.toString().includes(searchTerm)
    )

    const sortedPartners = [...filteredPartners].sort((a, b) => {
        if (!sortColumn) return 0

        let aValue: any = a[sortColumn as keyof Partner]
        let bValue: any = b[sortColumn as keyof Partner]

        if (sortColumn === 'idUserPartner') {
            aValue = a.idUserPartner.idPerson.namePerson
            bValue = b.idUserPartner.idPerson.namePerson
        }

        if (aValue < bValue) return sortDirection === 'asc' ? -1 : 1
        if (aValue > bValue) return sortDirection === 'asc' ? 1 : -1
        return 0
    })

    const toggleSort = (column: keyof Partner) => {
        if (sortColumn === column) {
            setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc')
        } else {
            setSortColumn(column)
            setSortDirection('asc')
        }
    }

    const renderSortIcon = (column: keyof Partner) => {
        if (sortColumn !== column) return <ChevronsUpDown className="ml-2 h-4 w-4"/>
        return sortDirection === 'asc' ? <ChevronUp className="ml-2 h-4 w-4"/> : <ChevronDown className="ml-2 h-4 w-4"/>
    }

    if (apiLoading) {
        return <div className="flex justify-center items-center h-screen">Cargando socios...</div>
    }

    if (apiError) {
        return <div className="flex justify-center items-center h-screen">Error al cargar los socios. Por favor, intente
            de nuevo más tarde.</div>
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
            <main className="container mx-auto px-4 py-8">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-3xl font-bold text-gray-800 dark:text-white">Socios</h1>
                    <Button onClick={() => setIsRegisterModalOpen(true)}>Registrar Nuevo Socio</Button>
                </div>
                <div className="mb-4">
                    <Input
                        placeholder="Buscar por nombre, usuario o documento"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="max-w-sm"
                    />
                </div>
                <div className="bg-white dark:bg-gray-800 rounded-lg shadow overflow-x-auto">
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead className="w-[100px]">
                                    <Button variant="ghost" onClick={() => toggleSort('idPartner')}>
                                        ID {renderSortIcon('idPartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('idUserPartner')}>
                                        Nombre {renderSortIcon('idUserPartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>Documento</TableHead>
                                <TableHead>Celular</TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('idUserPartner')}>
                                        Usuario {renderSortIcon('idUserPartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('amountPartner')}>
                                        Monto {renderSortIcon('amountPartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('creationDatePartner')}>
                                        Fecha de Creación {renderSortIcon('creationDatePartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    Acción
                                </TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {sortedPartners.map((partner) => (
                                <TableRow key={partner.idPartner}>
                                    <TableCell className="font-medium">{partner.idPartner}</TableCell>
                                    <TableCell>{partner.idUserPartner.idPerson.namePerson}</TableCell>
                                    <TableCell>{partner.idUserPartner.idPerson.documentPerson}</TableCell>
                                    <TableCell>{partner.idUserPartner.idPerson.cellphonePerson}</TableCell>
                                    <TableCell>{partner.idUserPartner.userName}</TableCell>
                                    <TableCell>{partner.amountPartner.toFixed(2)}</TableCell>
                                    <TableCell>{new Date(partner.creationDatePartner).toLocaleDateString()}</TableCell>
                                    <TableCell>
                                        <Button onClick={() => setIsRegisterModalOpen(true)}>Aprobar</Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
            </main>
            <RegisterPartnerModal
                isOpen={isRegisterModalOpen}
                onClose={() => setIsRegisterModalOpen(false)}
                onRegister={handleRegisterPartner}
            />
        </div>
    )
}

export default withAuth(Partners)