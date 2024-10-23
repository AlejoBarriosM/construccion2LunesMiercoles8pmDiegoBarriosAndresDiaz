'use client'

import {withAuth} from '@/components/withAuth'
import useSession from '@/hooks/useSession'



function Home() {
    const {session} = useSession() ?? {}

    const partner = (roleUser: string) => {
        if (roleUser === 'PARTNER') {
            return (
                <>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Suscripción:</span> {session?.partner?.typePartner}
                    </p>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Balance:</span> {session?.partner?.amountPartner}
                    </p>
                </>
            )
        }
    }

    return (
        <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
            <main className="container mx-auto px-4 py-8">
                <h1 className="text-3xl font-bold text-gray-800 dark:text-white mb-8">Bienvenido, {session?.user?.idPerson?.namePerson ?? 'Guest'}</h1>
                <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md">
                    <h2 className="text-2xl font-semibold text-gray-700 dark:text-gray-300 mb-4">Información de la
                        cuenta</h2>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Usuario:</span> {session?.user?.userName}
                    </p>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Documento:</span> {session?.user?.idPerson?.documentPerson}
                    </p>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Celular:</span> {session?.user?.idPerson?.cellphonePerson}
                    </p>
                    <p className="text-gray-600 dark:text-gray-400 mb-2">
                        <span className="font-semibold">Rol:</span> {session?.user?.roleUser}
                    </p>
                    {partner(session?.user?.roleUser ?? '')}

                </div>
            </main>
        </div>
    )
}

export default withAuth(Home)