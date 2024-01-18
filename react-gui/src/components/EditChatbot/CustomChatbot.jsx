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
    background: '#f5f8fb',
    fontFamily: 'Arial, sans-serif',
    headerBgColor: '#008587', // Change this to your desired header background color
    headerFontColor: '#fff',
    headerFontSize: '15px',
    botBubbleColor: '#008587', // Change this to your desired bot bubble color
    botFontColor: '#fff',
    userBubbleColor: '#fff',
    userFontColor: '#4a4a4a',
};
const CustomChatbot = () => {
    const [userInput, setUserInput] = useState('');
    const [messages, setMessages] = useState([]);
    const messagesEndRef = useRef(null);
    const [showError, setShowError] = useState(false);
    const [error, setError] = useState("");

    useEffect(() => {
        // Scrollt automatisch nach unten, wenn neue Nachrichten hinzugefügt werden
        messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }, [messages]);

    function handleUserInput() {
        const request: Message = {userInput: userInput};
            ChatbotService.analyzePost(request)
                .then((response) => {
                    setMessages((prevMessages) => [
                        ...prevMessages,
                        { role: 'user', content: userInput },
                        { role: 'bot', content: response },
                    ]);

                    // Leere die Benutzereingabe
                    setUserInput('');
                })
                .catch((error) => {
                    setShowError(true);
                    setError(error);
                });
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
                        <FileDropArea/>
                    </Grid>
                    <Grid item>
                        <div style={{ width: '600px', margin: 'auto', padding: '20px', borderRadius: '10px', boxShadow: '0 0 10px rgba(0,0,0,0.1)', backgroundColor: '#008587'}}>
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
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <TextField
                                    type="text"
                                    placeholder="Schreibe eine Nachricht..."
                                    value={userInput}
                                    onChange={(e) => setUserInput(e.target.value)}
                                    style={{ flex: 1, marginRight: '10px', backgroundColor: '#fff', color: '#008587' }}
                                />
                                <Button variant="contained" style={{ backgroundColor: '#fff', color: '#008587' }} onClick={handleUserInput}>
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
