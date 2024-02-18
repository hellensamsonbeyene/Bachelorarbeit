import React, { useRef,useState } from 'react';
import UploadFileIcon from '@mui/icons-material/UploadFile';
import ChatbotService from "../services/ChatbotService";


const FileDropArea = ({setShowPopUp, setPopUpMessage, setColorPopUp}) => {
    const fileInputRef = useRef(null);
    const [lastFileName, setLastFileName] = useState(null);


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
        setLastFileName(file.name); // Set lastFileName when a file is added
        if (file.type === 'text/plain') {
            const reader = new FileReader();

            reader.onload = (e) => {
                const content = e.target.result;
                const jsonContent = {
                    fileContent: content,
                };

                const formData = new FormData();
                formData.append('file', file);

                ChatbotService.uploadFile(formData)
                    .then(() => {
                        URL.revokeObjectURL(file.preview);
                        setShowPopUp(true);
                        setColorPopUp("success");
                        setPopUpMessage("Datei erfolgreich hochgeladen!");
                        console.log('Dateiinhalt (als JSON):', JSON.stringify(jsonContent, null, 2));
                    })
                    .catch((error) => {
                        setShowPopUp(true);
                        setColorPopUp("error");
                        console.log('Error', error);
                        setPopUpMessage(error.response.data);
                    });
            };
            file.preview = URL.createObjectURL(file);

            reader.readAsText(file);
        } else {
            setShowPopUp(true);
            setColorPopUp("error");
            setPopUpMessage("Invalides Dateiformat. Bitte laden Sie eine .txt Datei hoch.");
        }
    };

    const handleDragOver = (event) => {
        event.preventDefault();
    };

    const handleClick = (event) => {
        const { target = {} } = event || {};
        target.value = "";
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
            Ziehen Sie hier die .txt Datei rein <br/><br/> oder <br/><br/>Klicken Sie hier, um eine Datei hochzuladen. <br/><br/>
            {lastFileName && <div>Zuletzt hinzugefügte Datei: {lastFileName}</div>}
            <UploadFileIcon fontSize="large"/>
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

export default FileDropArea;
