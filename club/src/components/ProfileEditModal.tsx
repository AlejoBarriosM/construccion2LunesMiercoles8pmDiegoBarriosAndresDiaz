// /src/components/ProfileEditModal.tsx

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

interface User {
    id: number
    cedula: string
    nombre: string
    numeroCelular: string
    userName: string
}

interface ProfileEditModalProps {
    user: User
    isOpen: boolean
    onClose: () => void
    onSave: (updatedUser: User) => void
}

export function ProfileEditModal({ user, isOpen, onClose, onSave }: ProfileEditModalProps) {
    const [editedUser, setEditedUser] = useState(user)

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        setEditedUser(prev => ({ ...prev, [name]: value }))
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        onSave(editedUser)
        onClose()
    }

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>Editar Perfil</DialogTitle>
                    <DialogDescription>
                        Actualiza tu información personal aquí. Haz clic en guardar cuando hayas terminado.
                    </DialogDescription>
                </DialogHeader>
                <form onSubmit={handleSubmit}>
                    <div className="grid gap-4 py-4">
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="cedula" className="text-right">
                                Cédula
                            </Label>
                            <Input
                                id="cedula"
                                name="cedula"
                                value={editedUser.cedula}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="nombre" className="text-right">
                                Nombre
                            </Label>
                            <Input
                                id="nombre"
                                name="nombre"
                                value={editedUser.nombre}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="numeroCelular" className="text-right">
                                Número Celular
                            </Label>
                            <Input
                                id="numeroCelular"
                                name="numeroCelular"
                                value={editedUser.numeroCelular}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                        <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="userName" className="text-right">
                                Nombre de Usuario
                            </Label>
                            <Input
                                id="userName"
                                name="userName"
                                value={editedUser.userName}
                                onChange={handleChange}
                                className="col-span-3"
                            />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button type="submit">Guardar cambios</Button>
                    </DialogFooter>
                </form>
            </DialogContent>
        </Dialog>
    )
}