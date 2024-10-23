// /src/app/(authenticated)/invoices/page.tsx

'use client'

import { useState, useEffect, useCallback } from 'react'
import useApi from '@/hooks/useApi'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import { ChevronDown, ChevronUp, ChevronsUpDown } from 'lucide-react'
import Link from 'next/link'
import { useRouter } from 'next/navigation'
import useSession from '@/hooks/useSession'

interface Person {
    idPerson: number;
    documentPerson: number;
    namePerson: string; 
    cellphonePerson: number;
}

interface Partner {
    idPartner: number;
    idUserPartner: {
        idUser: number;
        idPerson: Person;
        userName: string;
        passwordUser: string;
        roleUser: string;
    };
    amountPartner: number;
    typePartner: string;
    creationDatePartner: string;
}

interface Invoice {
    idInvoice: number;
    idPerson: Person;
    idPartner: Partner;
    creationDateInvoice: string;
    amountInvoice: number;
    statusInvoice: string;
    detailsInvoice: string | null;
}

interface ApiResponse {
    body: Invoice[];
    statusCode: string;
    statusCodeValue: number;
}

export default function Invoices() {
    const { session } = useSession() ?? {}
    const [invoices, setInvoices] = useState<Invoice[]>([])
    const [searchTerm, setSearchTerm] = useState('')
    const [sortColumn, setSortColumn] = useState<keyof Invoice | ''>('')
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc')
    const { request, loading: apiLoading, error: apiError } = useApi<ApiResponse>()
    const router = useRouter()

    const loadInvoices = useCallback(async () => {
        try {
            let response
            if (session?.user?.roleUser === 'ADMIN') {
                response = await request('get', '/invoicesAPI/v1/invoices')
            } else {
                response = await request('get', '/invoicesAPI/v1/invoices/partner/' + session?.partner?.idPartner)
            }
            if (response && Array.isArray(response.body)) {
                setInvoices(response.body)
            }
        } catch (error) {
            console.error('Error al cargar las facturas:', error)
        }
    }, [request, session])

    useEffect(() => {
        loadInvoices()
    }, [loadInvoices])

    const filteredInvoices = invoices.filter(invoice =>
        invoice.idPerson.namePerson.toLowerCase().includes(searchTerm.toLowerCase()) ||
        invoice.idInvoice.toString().includes(searchTerm) ||
        invoice.statusInvoice.toLowerCase().includes(searchTerm.toLowerCase())
    )

    const sortedInvoices = [...filteredInvoices].sort((a, b) => {
        if (!sortColumn) return 0

        let aValue: any = a[sortColumn as keyof Invoice]
        let bValue: any = b[sortColumn as keyof Invoice]

        if (sortColumn === 'idPerson') {
            aValue = a.idPerson.namePerson
            bValue = b.idPerson.namePerson
        } else if (sortColumn === 'idPartner') {
            aValue = a.idPartner.idUserPartner.idPerson.namePerson
            bValue = b.idPartner.idUserPartner.idPerson.namePerson
        }

        if (aValue < bValue) return sortDirection === 'asc' ? -1 : 1
        if (aValue > bValue) return sortDirection === 'asc' ? 1 : -1
        return 0
    })

    const toggleSort = (column: keyof Invoice) => {
        if (sortColumn === column) {
            setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc')
        } else {
            setSortColumn(column)
            setSortDirection('asc')
        }
    }

    const renderSortIcon = (column: keyof Invoice) => {
        if (sortColumn !== column) return <ChevronsUpDown className="ml-2 h-4 w-4" />
        return sortDirection === 'asc' ? <ChevronUp className="ml-2 h-4 w-4" /> : <ChevronDown className="ml-2 h-4 w-4" />
    }

    function handleRegisterInvoice() {
        if (session?.user?.roleUser === 'ADMIN') {
            return null
        } else {
            return <Button onClick={() => router.push('/invoices/new')}>Registrar Nueva Factura</Button>
        }
    }

    if (apiLoading) {
        return <div className="flex justify-center items-center h-screen">Cargando facturas...</div>
    }

    if (apiError) {
        return <div className="flex justify-center items-center h-screen">Error al cargar las facturas. Por favor, intente de nuevo más tarde.</div>
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
            <main className="container mx-auto px-4 py-8">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-3xl font-bold text-gray-800 dark:text-white">Facturas</h1>
                    {handleRegisterInvoice()}
                </div>
                <div className="mb-4">
                    <Input
                        placeholder="Buscar por nombre, ID o estado"
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
                                    <Button variant="ghost" onClick={() => toggleSort('idInvoice')}>
                                        ID {renderSortIcon('idInvoice')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('idPerson')}>
                                        Cliente {renderSortIcon('idPerson')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('idPartner')}>
                                        Socio {renderSortIcon('idPartner')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('creationDateInvoice')}>
                                        Fecha {renderSortIcon('creationDateInvoice')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('amountInvoice')}>
                                        Monto {renderSortIcon('amountInvoice')}
                                    </Button>
                                </TableHead>
                                <TableHead>
                                    <Button variant="ghost" onClick={() => toggleSort('statusInvoice')}>
                                        Estado {renderSortIcon('statusInvoice')}
                                    </Button>
                                </TableHead>
                                <TableHead>Acciones</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {sortedInvoices.map((invoice) => (
                                <TableRow key={invoice.idInvoice}>
                                    <TableCell className="font-medium">{invoice.idInvoice}</TableCell>
                                    <TableCell>{invoice.idPerson.namePerson}</TableCell>
                                    <TableCell>{invoice.idPartner.idUserPartner.idPerson.namePerson}</TableCell>
                                    <TableCell>{new Date(invoice.creationDateInvoice).toLocaleDateString()}</TableCell>
                                    <TableCell>{invoice.amountInvoice.toFixed(2)}</TableCell>
                                    <TableCell>{invoice.statusInvoice}</TableCell>
                                    <TableCell>
                                        <Link href={`/invoices/${invoice.idInvoice}`}>
                                            <Button variant="outline" size="sm">
                                                Ver Detalle
                                            </Button>
                                        </Link>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </div>
            </main>
        </div>
    )
}