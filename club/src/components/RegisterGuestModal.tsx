// /src/app/components/RegisterGuestModal.tsx

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
import useSession from '@/hooks/useSession'

interface RegisterGuestData {
    userIdGuest: {
        idPerson: {
            documentPerson: number;
            namePerson: string;
            cellphonePerson: number;
        };
        userName: string;
        passwordUser: string;
    };
    idPartner: number;
}

interface RegisterGuestModalProps {
    isOpen: boolean;
    onClose: () => void;
    onRegister: () => Promise<void>;
}

interface ApiResponse {
    body: unknown | string;
    statusCodeValue: number;
    statusCode: string;
}

export function RegisterGuestModal({ isOpen, onClose, onRegister }: RegisterGuestModalProps) {

    const { session } = useSession() ?? {}
    const [guestData, setGuestData] = useState<RegisterGuestData>({
        userIdGuest: {
            idPerson: {
                documentPerson: 0,
                namePerson: '',
                cellphonePerson: 0
            },
            userName: '',
            passwordUser: ''
        },
        idPartner: session?.partner?.idPartner ?? 0
    })
    const { toast } = useToast()
    const { request, loading } = useApi<ApiResponse>()

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setGuestData(prev => ({
            ...prev,
            userIdGuest: {
                ...prev.userIdGuest,
                idPerson: {
                    ...prev.userIdGuest.idPerson,
                    [name]: name === 'namePerson' ? value : parseInt(value) || 0
                },
                [name]: value
            }
        }))
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()
        try {
            const response = await request('post', '/guestsAPI/v1/guest', guestData)
            if (response && response.statusCodeValue === 200) {
                toast({
                    title: "Invitado registrado",
                    description: "El nuevo invitado ha sido registrado exitosamente."
                })
                await onRegister()
                onClose()
                setGuestData({
                    userIdGuest: {
                        idPerson: {
                            documentPerson: 0,
                            namePerson: '',
                            cellphonePerson: 0
                        },
                        userName: '',
                        passwordUser: ''
                    },
                    idPartner: session?.partner?.idPartner ?? 0
                })
            } else {
                throw new Error(response.body.toString());
            }
        } catch (error){
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
        }
    }


    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Registrar Nuevo Invitado</DialogTitle>
                    <DialogDescription>
                        Ingresa los datos del nuevo invitado aquí. Asegúrate de que toda la información sea correcta.
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
                                value={guestData.userIdGuest.idPerson.documentPerson}
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
                                value={guestData.userIdGuest.idPerson.namePerson}
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
                                value={guestData.userIdGuest.idPerson.cellphonePerson}
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
                                value={guestData.userIdGuest.userName}
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
                                value={guestData.userIdGuest.passwordUser}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button type="submit" disabled={loading}>
                            {loading ?   'Registrando...' : 'Registrar Invitado'}
                        </Button>
                    </DialogFooter>
                </form>
            </DialogContent>
        </Dialog>
    )
}