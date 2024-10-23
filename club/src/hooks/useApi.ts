import { useState, useCallback } from 'react';
import api from '@/lib/api';
import { AxiosError } from 'axios';

interface ApiResponse<T> {
    headers: any;
    body: T;
    statusCode: string;
    statusCodeValue: number;
}

function useApi<T>() {
    const [state, setState] = useState<{
        data: T | null;
        error: ApiResponse<string> | null;
        loading: boolean;
    }>({
        data: null,
        error: null,
        loading: false,
    });

    const request = useCallback(async (method: "get" | "post" | "put" | "delete" | "patch", url: string, data?: any) => {
        setState({ data: null, error: null, loading: true });

        try {
            const response = await api[method](url, data);
            const apiResponse: ApiResponse<T> = response.data;
            setState({ data: apiResponse.body, error: null, loading: false });
            return apiResponse;
        } catch (error) {
            if (error instanceof AxiosError && error.response) {
                const apiError: ApiResponse<string> = error.response.data;
                setState({
                    data: null,
                    error: apiError,
                    loading: false
                });
                return apiError;
            } else {
                setState({
                    data: null,
                    error: {
                        body: error instanceof Error ? error.message : 'Error desconocido',
                        statusCode: 'Unknown',
                        statusCodeValue: 0,
                        headers: {}
                    },
                    loading: false
                });
                throw error;
            }
        }
    }, []);

    return { ...state, request };
}

export default useApi;