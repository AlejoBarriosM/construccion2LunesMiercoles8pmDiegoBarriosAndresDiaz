// /src/components/IncreaseBalanceModal.tsx

'use client'

import { useState } from 'react'
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
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

interface IncreaseBalanceModalProps {
    isOpen: boolean;
    onClose: () => void;
}

interface ApiResponse {
    body: unknown | string;
    statusCodeValue: number;
    statusCode: string;
}

export function IncreaseBalanceModal({ isOpen, onClose }: IncreaseBalanceModalProps) {
    const [amount, setAmount] = useState('')
    const [isLoading, setIsLoading] = useState(false)
    const { toast } = useToast()
    const { request } = useApi<ApiResponse>()
    const { session } = useSession()

    const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value
        if (/^\d*\.?\d*$/.test(value)) {
            setAmount(value)
        }
    }

    const handleConfirm = async () => {
        if (!session?.partner?.idPartner) {
            toast({
                title: "Error",
                description: "No se pudo obtener la información del socio.",
                variant: "destructive",
            })
            return
        }

        const amountValue = parseFloat(amount)
        if (isNaN(amountValue) || amountValue <= 0) {
            toast({
                title: "Error",
                description: "Por favor, ingrese un monto válido mayor que cero.",
                variant: "destructive",
            })
            return
        }

        setIsLoading(true)
        try {
            const response = await request(
                'patch',
                `/partnersAPI/v1/partner/${session.partner.idPartner}`,
                { amount: amountValue, increase: true }
            )

            if (response && response.statusCodeValue === 200) {
                toast({
                    title: "Éxito",
                    description: `Se ha aumentado su saldo en $${amountValue.toFixed(2)}.`,
                })
                // Actualizar la sesión con el nuevo saldo
                session.partner.amount = (session.partner.amount || 0) + amountValue
                setAmount('')
                onClose()
            } else {
                throw new Error(response.body.toString());
            }
        } catch (error) {
            let errorMessage = "Ocurrió un error al registrar el invitado.";

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
        }
    }

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Aumentar Saldo</DialogTitle>
                    <DialogDescription>
                        Ingrese el monto que desea agregar a su saldo actual.
                    </DialogDescription>
                </DialogHeader>
                <div className="grid gap-4 py-4">
                    <div className="grid grid-cols-4 items-center gap-4">
                        <label htmlFor="amount" className="text-right">
                            Monto
                        </label>
                        <Input
                            id="amount"
                            placeholder="0.00"
                            value={amount}
                            onChange={handleAmountChange}
                            className="col-span-3"
                        />
                    </div>
                </div>
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