import type {Metadata} from "next";
import {Inter} from 'next/font/google'
import "./globals.css";
import React from "react";
import {SessionProvider} from "@/components/SessionContext";

const inter = Inter({subsets: ['latin']})

export const metadata: Metadata = {
    title: 'Club App',
    description: 'Aplicación de administración del club',
};

export default function RootLayout({children,}: Readonly<{ children: React.ReactNode; }>) {
    return (
        <html lang="es" suppressHydrationWarning>
        <body className={`${inter.className} antialiasing`}>
        <SessionProvider>
            {children}
        </SessionProvider>
        </body>
        </html>
    );
}
