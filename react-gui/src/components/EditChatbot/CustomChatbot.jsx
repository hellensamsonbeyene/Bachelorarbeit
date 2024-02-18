import React, {useEffect, useRef, useState} from 'react';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import {ThemeProvider} from "styled-components";
import {Grid} from "@mui/material";
import FileDropArea from "../FileDropArea";
import PersonIcon from '@mui/icons-material/Person';
import SupportAgentIcon from '@mui/icons-material/SupportAgent';
import ChatbotService from "../../services/ChatbotService";
import {Message} from "../../models/Message";



const theme = {
    fontFamily: 'Arial, sans-serif',
    primary: '#008587', // Change this to your desired header background color
    headerFontSize: '15px',
    white: '#fff',
    secondary: '#4a4a4a',
};
const CustomChatbot = ({setShowPopUp, setPopUpMessage, setColorPopUp}) => {
    const [userInput, setUserInput] = useState('');
    const [messages, setMessages] = useState([]);
    const messagesEndRef = useRef(null);
    const [charCount, setCharCount] = useState(0);
    const maxChars = 500;

    const handleInputChange = (e) => {
        const inputText = e.target.value;
        setUserInput(inputText);

        // Aktualisieren Sie die Zeichenanzahl
        setCharCount(inputText.length);
    };

    useEffect(() => {
        // Scrollt automatisch nach unten, wenn neue Nachrichten hinzugefügt werden
        messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }, [messages]);

    function handleUserInput() {
        if (charCount >= maxChars){
            setShowPopUp(true);
            setColorPopUp("error");
            setPopUpMessage("Zu viele Zeichen. Bitte kürzen Sie Ihre Nachricht.");
        }else {
            const request: Message = {userInput: userInput};
            ChatbotService.analyzePost(request)
                .then((response) => {
                    setMessages((prevMessages) => [
                        ...prevMessages,
                        {role: 'user', content: userInput},
                        {role: 'bot', content: response},
                    ]);
                    // Leere die Benutzereingabe
                    setCharCount(0);
                    setUserInput('');
                })
                .catch((error) => {
                    setShowPopUp(true);
                    setColorPopUp("error");
                    setPopUpMessage(error.response.data);
                });
        }
    }

    const renderAvatar = (role) => {
        if (role === 'user') {
            return <PersonIcon fontSize="large"/>;
        } else if (role === 'bot') {
            return <SupportAgentIcon fontSize="large"/>;
        }
        return null;
    };

    return (
        <div style={{marginRight: '150px', marginTop: '50px'}}>
            <ThemeProvider theme={theme}>
                <Grid
                    container
                    justifyContent="center"
                    spacing={3}
                    alignItems="center"
                >
                    <Grid item>
                        <FileDropArea setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage} setColorPopUp={setColorPopUp}/>
                    </Grid>
                    <Grid item>
                        <div style={{ width: '600px', margin: 'auto', padding: '20px', borderRadius: '10px', boxShadow: '0 0 10px rgba(0,0,0,0.1)', backgroundColor: theme.primary}}>
                            <Typography variant="h5" gutterBottom style={{ textAlign: 'center', color:'#ffff' }}>
                                Chatbot
                            </Typography>
                            <div style={{ display: 'flex', flexDirection: 'column', height: '500px', overflowY: 'auto', marginBottom: '20px' ,backgroundColor: '#ffff'}}>
                                <div style={{padding:'10px',display: 'flex',flexDirection: 'column'}}>
                                {messages.map((message, index) => (
                                    <div key={index} style={{ display: 'flex', alignSelf: message.role === 'user' ? 'flex-end' : 'flex-start', marginBottom: '10px', justifyContent: message.role === 'user' ? 'flex-end' : 'flex-start', // Diese Zeile wurde angepasst
                                    }}>
                                        {message.role === 'bot' && (
                                            <div style={{ marginRight: '10px' }}>
                                                {renderAvatar(message.role)}
                                            </div>
                                        )}
                                        <Paper
                                            style={{
                                                wordWrap: 'break-word',
                                                padding: '10px',
                                                borderRadius: '15px',
                                                background: message.role === 'user' ? '#2196F3' : '#e0e0e0',
                                                color: message.role === 'user' ? '#fff' : '#000',
                                                maxWidth: '70%',
                                            }}
                                        >
                                            {message.content}
                                        </Paper>
                                        {message.role === 'user' && (
                                            <div style={{ marginLeft: '10px' }}>
                                                {renderAvatar(message.role)}
                                            </div>
                                        )}
                                    </div>
                                ))}
                                {/* Dieses leere div sorgt dafür, dass das Scrollen am unteren Rand bleibt */}
                                <div ref={messagesEndRef} />
                            </div>
                            </div>
                            <div style={{ display: 'flex', alignItems: 'start' }}>
                                <div style={{ display: 'flex', flexDirection: 'column', flexGrow: 1}}>
                                    <TextField
                                        multiline={4}
                                        type="text"
                                        placeholder="Schreibe eine Nachricht..."
                                        value={userInput}
                                        onChange={handleInputChange}
                                        style={{ width: '100%', marginBottom: '15px', backgroundColor: theme.white, color: theme.primary }}
                                    />
                                    <div style={{ fontSize: '18px', color: charCount > maxChars ? 'red' : theme.white }}>
                                        {charCount}/{maxChars} Zeichen
                                    </div>
                                </div>
                                <Button
                                    variant="contained"
                                    style={{ backgroundColor: theme.white, color: theme.primary, marginLeft: '10px' }}
                                    onClick={handleUserInput}
                                >
                                    Senden
                                </Button>
                            </div>
                        </div>
                    </Grid>
                </Grid>

            </ThemeProvider>
        </div>
    );
};

export default CustomChatbot;
