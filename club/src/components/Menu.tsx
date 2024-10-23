// /src/components/Menu.tsx

import React, { useState } from 'react'
import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { ThemeToggle } from '@/components/ThemeToggle'
import { UserCircle, Users, CreditCard, ClipboardList, Settings, LogOut } from 'lucide-react'

type UserRole = 'ADMIN' | 'PARTNER' | 'GUEST'

interface MenuItem {
    name: string;
    href?: string;
    icon: React.ElementType;
    onClick?: () => void;
}

const createMenuItems = (onEditProfile: () => void, onLogout: () => void): Record<UserRole, MenuItem[]> => ({
    ADMIN: [
        { name: 'Registrar Socios', href: '/partners', icon: UserCircle },
        { name: 'Historial de Facturas', href: '/invoices', icon: ClipboardList },
        { name: 'Promoción a VIP', href: '/vip-promotion', icon: Users },
        { name: 'Configuración', href: '/settings', icon: Settings },
        //{ name: 'Editar Perfil', icon: UserCircle, onClick: onEditProfile },
        { name: 'Cerrar Sesión', icon: LogOut, onClick: onLogout },
    ],
    PARTNER: [
        { name: 'Mis Invitados', href: '/guests', icon: Users },
        { name: 'Mis Facturas', href: '/invoices', icon: CreditCard },
        { name: 'Solicitar VIP', href: '/request-vip', icon: Users },
        //{ name: 'Editar Perfil', icon: UserCircle, onClick: onEditProfile },
        { name: 'Cerrar Sesión', icon: LogOut, onClick: onLogout },
    ],
    GUEST: [
        { name: 'Mis Consumos', href: '/my-consumptions', icon: CreditCard },
       // { name: 'Editar Perfil', icon: UserCircle, onClick: onEditProfile },
        { name: 'Cerrar Sesión', icon: LogOut, onClick: onLogout },
    ],
})

interface MenuProps {
    userRole: UserRole;
    onEditProfile: () => void;
    onLogout: () => void;
}

export function Menu({ userRole, onEditProfile, onLogout }: MenuProps) {
    const [isOpen, setIsOpen] = useState(false)
    const menuItems = createMenuItems(onEditProfile, onLogout)[userRole]

    const renderMenuItem = (item: MenuItem) => {
        const className = "text-gray-700 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-700 px-3 py-2 rounded-md text-sm font-medium flex items-center"
        const content = (
            <>
                <item.icon className="w-5 h-5 mr-2" />
                {item.name}
            </>
        )

        if (item.href) {
            return (
                <Link key={item.name} href={item.href} className={className}>
                    {content}
                </Link>
            )
        } else {
            return (
                <button key={item.name} onClick={item.onClick} className={className}>
                    {content}
                </button>
            )
        }
    }

    return (
        <nav className="bg-white dark:bg-gray-800 shadow-md">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-16">
                    <div className="flex items-center">
                        <Link href="/" className="flex-shrink-0">
                            <span className="text-2xl font-bold text-blue-600 dark:text-blue-400">Club App</span>
                        </Link>
                    </div>
                    <div className="hidden md:block">
                        <div className="ml-10 flex items-baseline space-x-4">
                            {menuItems.map(renderMenuItem)}
                        </div>
                    </div>
                    <div className="hidden md:block">
                        <ThemeToggle />
                    </div>
                    <div className="-mr-2 flex md:hidden">
                        <Button
                            variant="ghost"
                            className="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-blue-500"
                            onClick={() => setIsOpen(!isOpen)}
                        >
                            <span className="sr-only">Abrir menú principal</span>
                            {isOpen ? (
                                <svg className="block h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            ) : (
                                <svg className="block h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
                                </svg>
                            )}
                        </Button>
                    </div>
                </div>
            </div>
            {isOpen && (
                <div className="md:hidden">
                    <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
                        {menuItems.map(renderMenuItem)}
                    </div>
                    <div className="pt-4 pb-3 border-t border-gray-200 dark:border-gray-700">
                        <div className="flex items-center px-5">
                            <ThemeToggle />
                        </div>
                    </div>
                </div>
            )}
        </nav>
    )
}