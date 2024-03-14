import React from 'react';
//mui
import {
    Button,
    Grid,
} from '@mui/material';
import {ThemeProvider} from "styled-components";
//custom
import FileDropArea from "../customComponents/FileDropArea";
import CustomTooltip from "../customComponents/Tooltip";
import HowToChatbot from "../customComponents/HowToChatbot";
import VisualChatbot from "../VisualChatbot/VisualChatbot";
import ChatbotService from "../../services/ChatbotService";


/**
 * Chatbot-Komponente, FileDropArea und Chatbot
 */
const CustomChatbot = ({theme, setShowPopUp, setPopUpMessage, setColorPopUp}) => {

    //Funktion zum Zurücksetzen des Chatbots mit Anfrage ans Backend
    const resetChatbot = () => {
        ChatbotService.resetChatbot()
            .then(() => {
                setShowPopUp(true);
                setColorPopUp("success");
                setPopUpMessage("Chatbot erfolgreich zurückgesetzt!");
            })
            .catch(error => {
                setShowPopUp(true);
                setColorPopUp("success");
                setPopUpMessage("Fehler beim Zurücksetzen des Chatbots:", error);
            });
    };

    return (
        <div style={{display: 'flex', marginTop: '50px'}}>
            <ThemeProvider theme={theme}>
                <Grid container alignItems="center" justifyContent="flex-end" direction="column">
                    <Grid item xs={1}>
                        <FileDropArea setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage} setColorPopUp={setColorPopUp} />
                    </Grid>
                    <Grid item xs={5}>
                        <CustomTooltip title="Chatbot zurücksetzen">
                            <Button
                                variant="contained"
                                style={{ backgroundColor: theme.primary, color: theme.white }}
                                onClick={resetChatbot}
                            >Zurücksetzen auf BeispielChatbot</Button>
                        </CustomTooltip>
                    </Grid>
                </Grid>
                <Grid container justifyContent="center">
                    <Grid item>
                        <VisualChatbot theme={theme} setColorPopUp={setColorPopUp} setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage}/>
                    </Grid>
                </Grid>
                <Grid container justifyContent="center" alignItems="center">
                    <Grid>
                        <HowToChatbot theme={theme} />
                    </Grid>
                </Grid>
            </ThemeProvider>
        </div>
    );
};

export default CustomChatbot;
