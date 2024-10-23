// /src/hooks/useSession.tss

import { useSessionContext } from '@/components/SessionContext'

const useSession = () => {
    const { session, setSession, loading } = useSessionContext()

    const saveSession = (data: any) => {
        setSession(data)
    }

    const clearSession = () => {
        setSession(null)
    }


    return { session, saveSession, clearSession, loading }
}

export default useSession