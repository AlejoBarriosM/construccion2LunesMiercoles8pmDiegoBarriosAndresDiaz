'use client'

import React, { createContext, useState, useEffect, useContext } from 'react'

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
    roleUser: string;
}

interface Partner {
    idPartner: number;
    idUserPartner: User;
    amountPartner: number;
    typerPartner: string;
}

interface Guest {
    idGuest: number;
    userIdGuest: User;
    partnerIdGuest: Partner;
    statusGuest: string;
}

interface SessionData {
    user: User;
    person: Person;
    partner: Partner | any;
    guest: Guest | any;
    status: string;
}

interface SessionContextType {
    session: SessionData | null;
    setSession: React.Dispatch<React.SetStateAction<SessionData | null>>;
    loading: boolean;
}

const SessionContext = createContext<SessionContextType | undefined>(undefined)

export function SessionProvider({ children }: { children: React.ReactNode }) {
    const [session, setSession] = useState<SessionData | null>(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const storedSession = localStorage.getItem('userSession')
        if (storedSession) {
            setSession(JSON.parse(storedSession))
        }
        setLoading(false)
    }, [])

    useEffect(() => {
        if (session) {
            localStorage.setItem('userSession', JSON.stringify(session))
        } else {
            localStorage.removeItem('userSession')
        }
    }, [session])

    return (
        <SessionContext.Provider value={{ session, setSession, loading }}>
            {children}
        </SessionContext.Provider>
    )
}

export function useSessionContext() {
    const context = useContext(SessionContext)
    if (context === undefined) {
        throw new Error('useSessionContext must be used within a SessionProvider')
    }
    return context
}