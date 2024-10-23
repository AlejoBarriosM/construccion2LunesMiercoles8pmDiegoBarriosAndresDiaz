// /src/components/RegisterPartnerModal.tsx

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
import useApi from '@/hooks/useApi'

interface RegisterPartnerData {
    idUserPartner: {
        idPerson: {
            documentPerson: number;
            namePerson: string;
            cellphonePerson: number;
        };
        userName: string;
        passwordUser: string;
    };
}

interface RegisterPartnerModalProps {
    isOpen: boolean;
    onClose: () => void;
    onRegister: () => Promise<void>;
}

export function RegisterPartnerModal({ isOpen, onClose, onRegister }: RegisterPartnerModalProps) {
    const [partnerData, setPartnerData] = useState<RegisterPartnerData>({
        idUserPartner: {
            idPerson: {
                documentPerson: 0,
                namePerson: '',
                cellphonePerson: 0
            },
            userName: '',
            passwordUser: ''
        }
    })
    const { toast } = useToast()
    const { request, loading } = useApi()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setPartnerData(prev => ({
            idUserPartner: {
                ...prev.idUserPartner,
                idPerson: {
                    ...prev.idUserPartner.idPerson,
                    [name]: name === 'namePerson' ? value : parseInt(value) || 0
                },
                [name]: value
            }
        }))
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        try {
            const response = await request('post', '/partnersAPI/v1/partner', partnerData)
            if (response && response.statusCodeValue === 200) {
                toast({
                    title: "Socio registrado",
                    description: "El nuevo socio ha sido registrado exitosamente."
                })
                await onRegister()
                onClose()
                setPartnerData({
                    idUserPartner: {
                        idPerson: {
                            documentPerson: 0,
                            namePerson: '',
                            cellphonePerson: 0
                        },
                        userName: '',
                        passwordUser: ''
                    }
                })
            } else {
                throw new Error("La respuesta no fue exitosa")
            }
        } catch {
            toast({
                title: "Error",
                description: "Hubo un problema al registrar al socio. Por favor, intenta de nuevo.",
                variant: "destructive",
            })
        }
    }

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Registrar Nuevo Socio</DialogTitle>
                    <DialogDescription>
                        Ingresa los datos del nuevo socio aquí. Asegúrate de que toda la información sea correcta.
                    </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit}>
                    <div className="grid gap-4 py-4">
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="documentPerson" className="text-right">
                                Documento
                            </Label>
                            <Input
                                id="documentPerson"
                                name="documentPerson"
                                type="number"
                                value={partnerData.idUserPartner.idPerson.documentPerson}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="namePerson" className="text-right">
                                Nombre
                            </Label>
                            <Input
                                id="namePerson"
                                name="namePerson"
                                value={partnerData.idUserPartner.idPerson.namePerson}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="cellphonePerson" className="text-right">
                                Celular
                            </Label>
                            <Input
                                id="cellphonePerson"
                                name="cellphonePerson"
                                type="number"
                                value={partnerData.idUserPartner.idPerson.cellphonePerson}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="userName" className="text-right">
                                Usuario
                            </Label>
                            <Input
                                id="userName"
                                name="userName"
                                value={partnerData.idUserPartner.userName}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="passwordUser" className="text-right">
                                Contraseña
                            </Label>
                            <Input
                                id="passwordUser"
                                name="passwordUser"
                                type="password"
                                value={partnerData.idUserPartner.passwordUser}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button type="submit" disabled={loading}>
                            {loading ? 'Registrando...' : 'Registrar Socio'}
                        </Button>
                    </DialogFooter>
                </form>
            </DialogContent>
        </Dialog>
    )
}