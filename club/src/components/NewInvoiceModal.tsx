// /src/components/NewInvoiceModal.tsx

'use client'

import React, { useState } from 'react'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogFooter,
} from "@/components/ui/dialog"
import { useToast } from "@/hooks/use-toast"

interface InvoiceItem {
    idInvoiceDetail: number;
    item: number;
    descriptionInvoiceDetail: string;
    amountInvoiceDetail: number;
}

interface NewInvoiceModalProps {
    isOpen: boolean;
    onClose: () => void;
    onAddItem: (item: InvoiceItem) => void;
}

export function NewInvoiceModal({ isOpen, onClose, onAddItem }: NewInvoiceModalProps) {
    const [itemData, setItemData] = useState<InvoiceItem>({
        idInvoiceDetail: 0,
        item: 1,
        descriptionInvoiceDetail: '',
        amountInvoiceDetail: 0
    })
    const { toast } = useToast()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setItemData(prev => ({
            ...prev,
            [name]: name === 'item' || name === 'amountInvoiceDetail' ? parseFloat(value) : value
        }))
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        if (itemData.descriptionInvoiceDetail.trim() === '' || itemData.amountInvoiceDetail <= 0) {
            toast({
                title: "Error",
                description: "Por favor, complete todos los campos correctamente.",
                variant: "destructive",
            })
            return
        }
        onAddItem(itemData)
        setItemData({
            idInvoiceDetail: 0,
            item: itemData.item + 1,
            descriptionInvoiceDetail: '',
            amountInvoiceDetail: 0
        })
        onClose()
    }

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Agregar Item a la Factura</DialogTitle>
                    <DialogDescription>
                        Ingrese los detalles del item para la factura.
                    </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit}>
                    <div className="grid gap-4 py-4">
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="item" className="text-right">
                                Item
                            </Label>
                            <Input
                                id="item"
                                name="item"
                                type="number"
                                value={itemData.item}
                                onChange={handleChange}
                                className="col-span-3"
                                readOnly
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="descriptionInvoiceDetail" className="text-right">
                                Descripción
                            </Label>
                            <Input
                                id="descriptionInvoiceDetail"
                                name="descriptionInvoiceDetail"
                                value={itemData.descriptionInvoiceDetail}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="amountInvoiceDetail" className="text-right">
                                Monto
                            </Label>
                            <Input
                                id="amountInvoiceDetail"
                                name="amountInvoiceDetail"
                                type="number"
                                value={itemData.amountInvoiceDetail}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button type="submit">Agregar Item</Button>
                    </DialogFooter>
                </form>
            </DialogContent>
        </Dialog>
    )
}