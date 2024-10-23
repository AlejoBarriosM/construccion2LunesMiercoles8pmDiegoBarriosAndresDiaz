// /src/app/(auth)/login/page.tsx
'use client'

import React, {useState, useEffect} from 'react'
import {useRouter} from 'next/navigation'
import {Button} from "@/components/ui/button"
import {Input} from "@/components/ui/input"
import {Card, CardContent, CardDescription, CardHeader, CardTitle} from "@/components/ui/card"
import {Label} from "@/components/ui/label"
import {useToast} from "@/hooks/use-toast"
import useApi from '@/hooks/useApi'
import useSession from '@/hooks/useSession'

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
    passwordUser: string;
    roleUser: string;
}

interface Partner {
    idPartner: number;
    idUserPartner: User;
    amountPartner: number;
    typePartner: string;
}

interface Guest {
    idGuest: number;
    userIdGuest: User;
    partnerIdGuest: Partner;
    statusGuest: string;
}

interface LoginResponse {
    result: User | string;
    statusCode: number;
    timestamp: string;
}

export default function Login() {
    const [username, setUserName] = useState('')
    const [password, setPassword] = useState('')
    const router = useRouter()
    const {toast} = useToast()
    const {request, loading, error} = useApi<LoginResponse>()
    const {session, saveSession, loading: sessionLoading} = useSession()

    useEffect(() => {
        if (!sessionLoading && session) {
            router.push('/')
        }
    }, [session, sessionLoading, router])

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault()

        try {
            const response = await request('post', '/usersAPI/v1/users/login', {username, password})

            if (response.statusCodeValue === 200) {
                const roleHeader = response.headers.Role;

                if (roleHeader && roleHeader[0] === 'ADMIN') {
                    const userData = response.body as unknown as User;
                    saveSession({
                        user: {
                            idUser: userData.idUser,
                            idPerson: userData.idPerson,
                            userName: userData.userName,
                            roleUser: userData.roleUser
                        },
                        person: {
                            idPerson: userData.idPerson.idPerson,
                            documentPerson: userData.idPerson.documentPerson,
                            namePerson: userData.idPerson.namePerson,
                            cellphonePerson: userData.idPerson.cellphonePerson
                        },
                        statusCode: response.statusCode
                    })
                }

                if (roleHeader && roleHeader[0] === 'PARTNER') {
                    const partnerData = response.body as unknown as Partner;
                    saveSession({
                        user: {
                            idUser: partnerData.idUserPartner.idUser,
                            idPerson: partnerData.idUserPartner.idPerson,
                            userName: partnerData.idUserPartner.userName,
                            roleUser: partnerData.idUserPartner.roleUser
                        },
                        person: {
                            idPerson: partnerData.idUserPartner.idPerson.idPerson,
                            documentPerson: partnerData.idUserPartner.idPerson.documentPerson,
                            namePerson: partnerData.idUserPartner.idPerson.namePerson,
                            cellphonePerson: partnerData.idUserPartner.idPerson.cellphonePerson
                        },
                        partner: {
                            idPartner: partnerData.idPartner,
                            amountPartner: partnerData.amountPartner,
                            typePartner: partnerData.typePartner
                        },
                        statusCode: response.statusCode
                    })
                }

                if (roleHeader && roleHeader[0] === 'GUEST') {
                    const guestData = response.body as unknown as Guest;
                    saveSession({
                        user: {
                            idUser: guestData.userIdGuest.idUser,
                            idPerson: guestData.userIdGuest.idPerson,
                            userName: guestData.userIdGuest.userName,
                            roleUser: guestData.userIdGuest.roleUser
                        },
                        person: {
                            idPerson: guestData.userIdGuest.idPerson.idPerson,
                            documentPerson: guestData.userIdGuest.idPerson.documentPerson,
                            namePerson: guestData.userIdGuest.idPerson.namePerson,
                            cellphonePerson: guestData.userIdGuest.idPerson.cellphonePerson
                        },
                        partner: {
                            idPartner: guestData.partnerIdGuest.idPartner,
                            amountPartner: guestData.partnerIdGuest.amountPartner,
                            typePartner: guestData.partnerIdGuest.typePartner
                        },
                        guest: {
                            idGuest: guestData.idGuest,
                            statusGuest: guestData.statusGuest
                        },
                        statusCode: response.statusCode
                    })
                }

                toast({
                    title: "Inicio de sesión exitoso",
                    description: `Bienvenido, ${session?.person?.namePerson}`,
                })
                router.push('/')
            }else {
                // Manejar respuesta no exitosa
                toast({
                    title: "Error",
                    description: typeof response.body === 'string' ? response.body : "Ocurrió un error al iniciar sesión.",
                    variant: "destructive",
                });
            }

        } catch {
            toast({
                title: "Error de inicio de sesión",
                description: "Hubo un problema al iniciar sesión. Por favor, intenta de nuevo.",
                variant: "destructive",
            })
        }
    }

    if (sessionLoading) {
        return <div className="flex justify-center items-center h-screen">Cargando...</div>
    }

    if (session) {
        return null;
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-blue-500 to-purple-600">
            <Card className="w-full max-w-md shadow-xl">
                <CardHeader className="space-y-1">
                    <CardTitle className="text-2xl font-bold text-center">Bienvenido al Club</CardTitle>
                    <CardDescription className="text-center">Inicia sesión para acceder a tu cuenta</CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div className="space-y-2">
                            <Label htmlFor="userName" className="text-sm font-medium">
                                Nombre de usuario
                            </Label>
                            <Input
                                id="userName"
                                placeholder="Ingresa tu nombre de usuario"
                                value={username}
                                onChange={(e) => setUserName(e.target.value)}
                                required
                                className="w-full px-3 py-2 border rounded-md"
                            />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="password" className="text-sm font-medium">
                                Contraseña
                            </Label>
                            <Input
                                id="password"
                                type="password"
                                placeholder="Ingresa tu contraseña"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                className="w-full px-3 py-2 border rounded-md"
                            />
                        </div>
                        <Button
                            className="w-full bg-gradient-to-r from-blue-600 to-purple-600 hover:from-blue-700 hover:to-purple-700 text-white font-bold py-2 px-4 rounded-md transition duration-300 ease-in-out transform hover:scale-105"
                            type="submit"
                            disabled={loading}
                        >
                            {loading ? 'Iniciando sesión...' : 'Iniciar sesión'}
                        </Button>
                    </form>
                    {error && (
                        <p className="mt-4 text-center text-red-500">
                            {typeof error === 'string' ? error : 'An unexpected error occurred'}
                        </p>
                    )}
                </CardContent>
            </Card>
        </div>
    )
}