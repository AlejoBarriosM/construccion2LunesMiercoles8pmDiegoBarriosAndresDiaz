// /src/components/VipConfirmationModal.tsx

'use client'

import { useState } from 'react'
import { Button } from "@/components/ui/button"
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog"
import { useToast } from "@/hooks/use-toast"
import useApi from '@/hooks/useApi'
import useSession from '@/hooks/useSession'

interface VipConfirmationModalProps {
    isOpen: boolean;
    onClose: () => void;
}

interface ApiResponse {
    body: unknown | string;
    statusCodeValue: number;
    statusCode: string;
}

export function VipConfirmationModal({ isOpen, onClose }: VipConfirmationModalProps) {
    const [isLoading, setIsLoading] = useState(false)
    const { toast } = useToast()
    const { session } = useSession() ?? {}
    const { request } = useApi<ApiResponse>()

    const handleConfirm = async () => {
        if (!session?.partner?.idPartner) {
            toast({
                title: "Error",
                description: "No se pudo obtener la información del socio.",
                variant: "destructive",
            })
            return
        }

        setIsLoading(true)
        try {
            const response = await request('patch', `/partnersAPI/v1/partner/${session.partner.idPartner}`, { typePartner: "PENDIENTE" })

            if (response && response.statusCodeValue === 200) {
                toast({
                    title: "Éxito",
                    description: "Su solicitud para ser VIP ha sido procesada con éxito.",
                })

            } else {
                throw new Error(response.body.toString());
            }
        } catch (error) {
            let errorMessage = "Ocurrió un error al registrar la solicitud.";

            if (error instanceof Error) {
                errorMessage = error.message;
            } else if (typeof error === 'object' && error !== null && 'body' in error) {
                errorMessage = (error as { body: string }).body;
            }

            toast({
                title: "Error",
                description: errorMessage,
                variant: "destructive",
            });
        } finally {
            setIsLoading(false)
            onClose()
        }
    }

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Confirmar solicitud VIP</DialogTitle>
                    <DialogDescription>
                        ¿Está seguro de que desea solicitar el cambio a socio VIP? Este cambio puede conllevar beneficios adicionales y posibles cambios en su cuota.
                    </DialogDescription>
                </DialogHeader>
                <DialogFooter>
                    <Button variant="outline" onClick={onClose} disabled={isLoading}>
                        Cancelar
                    </Button>
                    <Button onClick={handleConfirm} disabled={isLoading}>
                        {isLoading ? "Procesando..." : "Confirmar"}
                    </Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>
    )
}