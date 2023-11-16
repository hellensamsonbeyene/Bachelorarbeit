// apiActions.js

import axios from 'axios';

export const apiRequest = () => ({ type: 'API_REQUEST' });
export const apiSuccess = (data) => ({ type: 'API_SUCCESS', payload: data });
export const apiFailure = (error) => ({ type: 'API_FAILURE', payload: error });

export const analyzeAnswer = () => {
    return async (dispatch) => {
        dispatch(apiRequest());
        try {
            const response = await axios.get('https://api.example.com/data');
            dispatch(apiSuccess(response.data));
        } catch (error) {
            dispatch(apiFailure(error.message));
        }
    };
};

export const postRequest = (postData) => {
    return async (dispatch) => {
        try {
            // Optional: Dispatch eine Aktion, um den Ladezustand anzuzeigen
            dispatch({ type: 'POST_REQUEST_START' });

            // POST-Anfrage mit axios durchführen
            const response = await axios.post('http://localhost:8080/analyze', postData);

            // Optional: Dispatch eine Aktion, um den Erfolg anzuzeigen
            dispatch({ type: 'POST_REQUEST_SUCCESS', payload: response.data });
        } catch (error) {
            // Optional: Dispatch eine Aktion, um den Fehler anzuzeigen
            dispatch({ type: 'POST_REQUEST_FAILURE', payload: error.message });
        }
    };
};
