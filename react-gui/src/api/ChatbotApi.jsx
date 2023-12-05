// apiService.js

const API_BASE_URL = 'https:localhost:8080'; // Ersetze dies durch deine API-Basis-URL

export const addPost = (userInput) => {
    return async () => {
        try {
            // Logik für den API-Aufruf oder andere asynchrone Operationen
            const response = await fetch(`${API_BASE_URL}/analyze`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ userInput }),
            });

            // Hier kannst du weitere Logik für die Antwortverarbeitung implementieren
           // dispatch(analyzeSuccess(data)); // Beispiel für eine weitere Aktion, die den Redux-Store aktualisiert

            return await response.json(); // Die Antwort wird an die Komponente weitergegeben
        } catch (error) {
            console.error('Fehler bei der Analyse:', error);
            throw error;
        }
    };
};
