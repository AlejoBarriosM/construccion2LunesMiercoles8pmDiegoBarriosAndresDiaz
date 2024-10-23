// /src/app/(authenticated)/guests/page.tsx

'use client'

import { withAuth } from '@/components/withAuth'
import { useState, useEffect, useCallback } from 'react'
import { RegisterGuestModal } from '@/components/RegisterGuestModal'
import useApi from '@/hooks/useApi'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import useSession from '@/hooks/useSession'
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { ChevronDown, ChevronUp, ChevronsUpDown } from 'lucide-react'

interface Guest {
    idGuest: number;
    userIdGuest: {
        idUser: number;
        idPerson: {
            idPerson: number;
            documentPerson: number;
            namePerson: string;
            cellphonePerson: number;
        };
        userName: string;
        passwordUser: string;
        roleUser: string;
    };
    partnerIdGuest: {
        idPartner: number;
        idUserPartner: {
            idUser: number;
            idPerson: {
                idPerson: number;
                documentPerson: number;
                namePerson: string;
                cellphonePerson: number;
            };
            userName: string;
            passwordUser: string;
            roleUser: string;
        };
        amountPartner: number;
        typePartner: string;
        creationDatePartner: string;
    };
    statusGuest: string;
}

interface ApiResponse {
    body: Guest[];
    statusCode: string;
    statusCodeValue: number;
}

function Guests() {
    const { session } = useSession() ?? {}
    const [isRegisterModalOpen, setIsRegisterModalOpen] = useState(false)
    const [guests, setGuests] = useState<Guest[]>([])
    const [searchTerm, setSearchTerm] = useState('')
    const [sortColumn, setSortColumn] = useState<keyof Guest | ''>('')
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc')
    const { request, loading: apiLoading, error: apiError } = useApi<ApiResponse>()

    const loadGuests = useCallback(async () => {
        try {

            const partnerId = session?.partner?.idPartner;
            const response = await request('get', `/guestsAPI/v1/guest/partner/${partnerId}`)
            if (response && Array.isArray(response.body)) {
                setGuests(response.body)
            }
        } catch (error) {
            console.error('Error al cargar los invitados:', error)
        }
    }, [request])

    useEffect(() => {
        loadGuests()
    }, [loadGuests])

    const handleRegisterGuest = async () => {
        await loadGuests()
    }

    const filteredGuests = guests.filter(guest =>
        guest.userIdGuest.idPerson.namePerson.toLowerCase().includes(searchTerm.toLowerCase()) ||
        guest.userIdGuest.userName.toLowerCase().includes(searchTerm.toLowerCase()) ||
        guest.userIdGuest.idPerson.documentPerson.toString().includes(searchTerm)
    )

    const sortedGuests = [...filteredGuests].sort((a, b) => {
        if (!sortColumn) return 0

        let aValue: any = a[sortColumn as keyof Guest]
        let bValue: any = b[sortColumn as keyof Guest]

        if (sortColumn === 'userIdGuest') {
            aValue = a.userIdGuest.idPerson.namePerson
            bValue = b.userIdGuest.idPerson.namePerson
        }

        if (aValue < bValue) return sortDirection === 'asc' ? -1 : 1
        if (aValue > bValue) return sortDirection === 'asc' ? 1 : -1
        return 0
    })

    const toggleSort = (column: keyof Guest) => {
        if (sortColumn === column) {
            setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc')
        } else {
            setSortColumn(column)
            setSortDirection('asc')
        }
    }

    const renderSortIcon = (column: keyof Guest) => {
        if (sortColumn !== column) return <ChevronsUpDown className="ml-2 h-4 w-4" />
        return sortDirection === 'asc' ? <ChevronUp className="ml-2 h-4 w-4" /> : <ChevronDown className="ml-2 h-4 w-4" />
    }

    if (apiLoading) {
        return <div className="flex justify-center items-center h-screen">Cargando invitados...</div>
    }

    if (apiError) {
        return <div className="flex justify-center items-center h-screen">Error al cargar los invitados. Por favor, intente de nuevo más tarde.</div>
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
            <main className="container mx-auto px-4 py-8">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-3xl font-bold text-gray-800 dark:text-white">Invitados</h1>
                    <Button onClick={() => setIsRegisterModalOpen(true)}>Registrar Nuevo Invitado</Button>
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
                                    <Button variant="ghost" onClick={() => toggleSort('idGuest')}>
                                        ID {renderSortIcon('idGuest')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('userIdGuest')}>
                                        Nombre {renderSortIcon('userIdGuest')}
                                    </Button>
                                </TableHead>
                                <TableHead>Documento</TableHead>
                                <TableHead>Celular</TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('userIdGuest')}>
                                        Usuario {renderSortIcon('userIdGuest')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('statusGuest')}>
                                        Estado {renderSortIcon('statusGuest')}
                                    </Button>
                                </TableHead>
                                <TableHead>Socio Anfitrión</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {sortedGuests.map((guest) => (
                                <TableRow key={guest.idGuest}>
                                    <TableCell className="font-medium">{guest.idGuest}</TableCell>
                                    <TableCell>{guest.userIdGuest.idPerson.namePerson}</TableCell>
                                    <TableCell>{guest.userIdGuest.idPerson.documentPerson}</TableCell>
                                    <TableCell>{guest.userIdGuest.idPerson.cellphonePerson}</TableCell>
                                    <TableCell>{guest.userIdGuest.userName}</TableCell>
                                    <TableCell>{guest.statusGuest}</TableCell>
                                    <TableCell>{guest.partnerIdGuest.idUserPartner.idPerson.namePerson}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
            </main>
            <RegisterGuestModal
                isOpen={isRegisterModalOpen}
                onClose={() => setIsRegisterModalOpen(false)}
                onRegister={handleRegisterGuest}
            />
        </div>
    )
}

export default withAuth(Guests)