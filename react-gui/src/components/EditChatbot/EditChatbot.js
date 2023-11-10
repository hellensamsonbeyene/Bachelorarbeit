import React, {Component} from 'react';
import ChatBot from 'react-simple-chatbot';
import {Grid} from "@mui/material";
import FileDropArea from "../FileDropArea";
import {ThemeProvider} from 'styled-components';


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

class EditChatbot extends Component {

    render() {
        return (
            <div style={{marginRight: '50px', marginTop: '150px'}}>
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
                            <ChatBot style={{width: '600px'}}
                                     steps={[
                                         {
                                             id: 'intro',
                                             message: 'Hallo, wie kann ich Dir helfen?',
                                             trigger: 'intro-user',
                                         },
                                         {
                                             id: 'intro-user',
                                             user: true,
                                             end: true,
                                         },
                                     ]}
                            />
                        </Grid>
                    </Grid>
                </ThemeProvider>
            </div>
        );
    }
}

export default EditChatbot;
