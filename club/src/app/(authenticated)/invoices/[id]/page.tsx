// /src/app/(authenticated)/invoices/[id]/page.tsx

'use client'

import {useState, useEffect} from 'react'
import useApi from '@/hooks/useApi'
import {useParams, useRouter} from 'next/navigation'
import {Button} from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import {ArrowLeft} from 'lucide-react'
import Link from 'next/link'
import {useToast} from "@/hooks/use-toast"
import useSession from '@/hooks/useSession'
import {NewInvoiceModal} from '@/components/NewInvoiceModal'

interface Person {
    idPerson: number;
    documentPerson: number;
    namePerson: string;
    cellphonePerson: number;
}

interface User {
    idUser: number;
    idPerson: Person;
    userName: string;
    roleUser: string;
}

interface Partner {
    idPartner: number;
    idUserPartner: User;
    amountPartner: number;
    typePartner: string;
}

interface InvoiceItem {
    idInvoiceDetail: number;
    item: number;
    descriptionInvoiceDetail: string;
    amountInvoiceDetail: number;
}

interface Invoice {
    idInvoice?: number | null;
    idPerson?: number | Person;
    idPartner?: number | Partner;
    creationDateInvoice?: string | null;
    amountInvoice: number;
    statusInvoice: string;
    detailInvoice: InvoiceItem[];
}

interface ApiResponse {
    body: Invoice | Invoice[];
    statusCode: string;
    statusCodeValue: number;
}

export default function InvoiceDetail() {
    const [invoice, setInvoice] = useState<Invoice | null>(null)
    const [isNewInvoiceModalOpen, setIsNewInvoiceModalOpen] = useState(false)
    const {request, loading: apiLoading, error: apiError} = useApi<ApiResponse>()
    const params = useParams()
    const router = useRouter()
    const {toast} = useToast()
    const {session} = useSession() ?? {}
    const id = params.id as string

    useEffect(() => {
        const loadInvoice = async () => {
            if (id !== 'new') {
                try {
                    const response = await request('get', `/invoicesAPI/v1/invoices?id=${id}`)
                    if (response && response.body) {
                        if (Array.isArray(response.body)) {
                            setInvoice(response.body[0])
                        } else {
                            setInvoice(response.body as unknown as Invoice)
                        }
                    } else {
                        throw new Error('No se encontró la factura')
                    }
                } catch (error) {
                    console.error('Error al cargar la factura:', error)
                    toast({
                        title: "Error",
                        description: "No se pudo cargar la factura. Por favor, intente de nuevo.",
                        variant: "destructive",
                    })
                }
            } else {
                setInvoice({
                    idPerson: session?.user?.idPerson.idPerson ?? 0,
                    idPartner: session?.partner.idPartner ?? 0,
                    amountInvoice: 0,
                    statusInvoice: 'PENDING',
                    detailInvoice: []
                })
            }
        }

        loadInvoice()
    }, [id, request, toast, session])

    const handleAddItem = (item: InvoiceItem) => {
        if (invoice) {
            const updatedDetails = [...invoice.detailInvoice, item]
            const totalAmount = updatedDetails.reduce((sum, detail) => sum + detail.amountInvoiceDetail, 0)
            setInvoice({
                ...invoice,
                detailInvoice: updatedDetails,
                amountInvoice: totalAmount
            })
        }
    }

    const handleSaveInvoice = async () => {
        if (invoice) {
            try {
                const response = await request('post', '/invoicesAPI/v1/invoices', invoice)
                if (response && response.statusCodeValue === 200) {
                    toast({
                        title: "Éxito",
                        description: "La factura ha sido guardada exitosamente.",
                    })
                    router.push('/invoices')
                } else {
                    throw new Error('Error al guardar la factura')
                }
            } catch (error) {
                console.error('Error al guardar la factura:', error)
                toast({
                    title: "Error",
                    description: "No se pudo guardar la factura. Por favor, intente de nuevo.",
                    variant: "destructive",
                })
            }
        }
    }

    if (apiLoading) {
        return <div className="flex justify-center items-center h-screen">Cargando detalles de la factura...</div>
    }

    if (apiError || !invoice) {
        return (
            <div className="flex flex-col justify-center items-center h-screen">
                <p className="text-xl mb-4">Error al cargar los detalles de la factura.</p>
                <Link href="/invoices">
                    <Button variant="outline">
                        <ArrowLeft className="mr-2 h-4 w-4"/> Volver a Facturas
                    </Button>
                </Link>
            </div>
        )
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8">
            <div className="container mx-auto px-4">
                <Link href="/invoices">
                    <Button variant="outline" className="mb-4">
                        <ArrowLeft className="mr-2 h-4 w-4"/> Volver a Facturas
                    </Button>
                </Link>
                <Card className="w-full max-w-2xl mx-auto">
                    <CardHeader>
                        <CardTitle>{id === 'new' ? 'Nueva Factura' : `Factura #${invoice.idInvoice}`}</CardTitle>
                        <CardDescription>Detalles de la factura</CardDescription>
                    </CardHeader>
                    <CardContent className="grid gap-4">
                        <div className="grid grid-cols-2 gap-4">
                            <div>
                                <h3 className="font-semibold">Persona</h3>
                                {typeof invoice.idPerson === 'object' && (
                                    <>
                                        <p>{invoice.idPerson.namePerson || session?.person?.namePerson}</p>
                                        <p>Documento: {invoice.idPerson.documentPerson || session?.person?.documentPerson}</p>
                                        <p>Teléfono: {invoice.idPerson.cellphonePerson || session?.person?.cellphonePerson}</p>
                                    </>
                                )}
                            </div>
                            <div>
                                <h3 className="font-semibold">Socio</h3>
                                {typeof invoice.idPartner === 'object' && (
                                    <>
                                        <p>{invoice.idPartner.idUserPartner?.idPerson?.namePerson || session?.partner?.idPartner}</p>
                                        <p>Tipo: {invoice.idPartner.typePartner || session?.partner?.typePartner}</p>
                                    </>
                                )}
                            </div>
                        </div>
                        <div>
                            <h3 className="font-semibold">Detalles de la Factura</h3>
                            <p>Fecha: {new Date(invoice.creationDateInvoice ?? new Date()).toLocaleString()}</p>
                            <p>Monto Total: ${invoice.amountInvoice.toFixed(2)}</p>
                            <p>Estado: {invoice.statusInvoice}</p>
                        </div>
                        {invoice.detailInvoice && invoice.detailInvoice.length > 0 && (
                            <div>
                                <h3 className="font-semibold mb-2">Detalles de los Items</h3>
                                <div className="overflow-x-auto">
                                    <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                                        <thead className="bg-gray-50 dark:bg-gray-800">
                                        <tr>
                                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Item</th>
                                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Descripción</th>
                                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Monto</th>
                                        </tr>
                                        </thead>
                                        <tbody
                                            className="bg-white dark:bg-gray-700 divide-y divide-gray-200 dark:divide-gray-600">
                                        {invoice.detailInvoice.map((detail) => (
                                            <tr key={detail.idInvoiceDetail}>
                                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{detail.item}</td>
                                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">{detail.descriptionInvoiceDetail}</td>
                                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-gray-100">${detail.amountInvoiceDetail.toFixed(2)}</td>
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        )}
                    </CardContent>
                    <CardFooter className="flex justify-between">
                        {id === 'new' && (
                            <Button onClick={() => setIsNewInvoiceModalOpen(true)}>Agregar Item</Button>
                        )}
                        {id === 'new' && (
                            <Button onClick={handleSaveInvoice}>Guardar Factura</Button>
                        )}
                        {id !== 'new' && (
                            <Button className="w-full">Imprimir Factura</Button>
                        )}
                    </CardFooter>
                </Card>
            </div>
            <NewInvoiceModal
                isOpen={isNewInvoiceModalOpen}
                onClose={() => setIsNewInvoiceModalOpen(false)}
                onAddItem={handleAddItem}
            />
        </div>
    )
}