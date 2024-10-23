'use client'

import React, { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import useSession from '@/hooks/useSession'

export function withAuth<P extends object>(
    WrappedComponent: React.ComponentType<P>
) {
    return function AuthComponent(props: P) {
        const router = useRouter()
        const { session, loading } = useSession()

        useEffect(() => {
            if (!loading && !session) {
                router.push('/login')
            }
        }, [session, loading, router])

        if (loading) {
            return <div className="flex justify-center items-center h-screen">Cargando...</div>
        }

        if (!session || !session.user) {
            return null
        }

        return <WrappedComponent {...props} />
    }
}