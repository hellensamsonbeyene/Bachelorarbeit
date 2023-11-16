import React, { useState, useRef } from 'react';
import UploadFileIcon from '@mui/icons-material/UploadFile';

const FileDropArea = () => {
    const [successMessage, setSuccessMessage] = useState(null);
    const fileInputRef = useRef(null);

    const handleDrop = (event) => {
        event.preventDefault();

        const file = event.dataTransfer.files[0];

        if (file) {
            handleFile(file);
        }
    };

    const handleFileInput = (event) => {
        const file = event.target.files[0];

        if (file) {
            handleFile(file);
        }
    };

    const handleFile = (file) => {
        if (file.type === 'text/plain') {
            const reader = new FileReader();

            reader.onload = (e) => {
                const content = e.target.result;
                const jsonContent = {
                    fileContent: content,
                };

                // Log the JSON content to the console
                console.log('File Content (as JSON):', JSON.stringify(jsonContent, null, 2));

                // Set the success message
                setSuccessMessage('Datei erfolgreich hochgeladen!');
            };

            reader.readAsText(file);
        } else {
            setSuccessMessage(null);
            console.error('Invalides Dateiformat. Bitte laden Sie eine .txt Datei hoch.');
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    const handleClick = () => {
        // Trigger file input click
        fileInputRef.current.click();
    };

    return (
        <div
            style={{
                border: '2px dashed #aaa',
                borderRadius: '5px',
                padding: '20px',
                textAlign: 'center',
                margin: '20px',
                cursor: 'pointer',
            }}
            onDrop={handleDrop}
            onDragOver={handleDragOver}
            onClick={handleClick}
        >
            Ziehen Sie hier die .txt Datei rein <br/><br/> oder <br/><br/>Klicken Sie hier, um eine Datei hochzuladen. <br/><br/> <UploadFileIcon fontSize="large"/>
            <input
                type="file"
                accept=".txt"
                style={{ display: 'none' }}
                ref={fileInputRef}
                onChange={handleFileInput}
            />
        </div>
    );
};


export default FileDropArea
