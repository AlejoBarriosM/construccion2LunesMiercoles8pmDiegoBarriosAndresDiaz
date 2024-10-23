'use client'

import React, {useEffect, useState} from 'react'
import { useRouter } from 'next/navigation'
import { Menu } from '@/components/Menu'
import useSession from '@/hooks/useSession'
import {ThemeProvider} from "next-themes";
import {ProfileEditModal} from "@/components/ProfileEditModal";
import { Toaster } from "@/components/ui/toaster";

export default function AuthenticatedLayout({ children }: { children: React.ReactNode }) {
    const router = useRouter()
    const { session, clearSession, loading } = useSession()
    const [isProfileModalOpen, setIsProfileModalOpen] = useState(false)

    useEffect(() => {
        if (!loading && !session) {
            router.push('/login')
        }
    }, [session, loading, router])

    const handleSaveProfile = (updatedUser: any) => {
        console.log('Perfil actualizado:', updatedUser)
    }

    const handleLogout = () => {
        clearSession()
        router.push('/login')
    }

    if (loading) {
        return <div className="flex justify-center items-center h-screen">Cargando...</div>
    }

    if (!session || !session.user) {
        return null
    }

    return (
        <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
        <>
            <Menu
                userRole={session.user.roleUser as 'ADMIN' | 'PARTNER' | 'GUEST'}
                onEditProfile={() => setIsProfileModalOpen(true)}
                onLogout={handleLogout}
            />
            <main className="container mx-auto px-4 py-8">
                {React.cloneElement(children as React.ReactElement, { session })}
            </main>
            <ProfileEditModal
                user={{
                    id: session.user.idUser,
                    cedula: session.user.idPerson.documentPerson.toString(),
                    nombre: session.user.idPerson.namePerson,
                    numeroCelular: session.user.idPerson.cellphonePerson.toString(),
                    userName: session.user.userName
                }}
                isOpen={isProfileModalOpen}
                onClose={() => setIsProfileModalOpen(false)}
                onSave={handleSaveProfile}
            />
            <Toaster />
        </>
        </ThemeProvider>
    )
}