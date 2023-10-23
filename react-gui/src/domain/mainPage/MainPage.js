// react
import React from "react";

import {ThemeProvider, createTheme} from '@mui/material/styles';
import NavigationBar from "../../NavigationBar/NavigationBar";
import EditChatbot from "../../components/EditChatbot/EditChatbot";
//custom



// default colors
const palette = {
    primary: '#008587', // buttons, sliders, selected nodes
    secondary: '#3DC72E',
}

const MainPage = () => {

      // eslint-disable-next-line
    const [getColorPalette, setColorPalette] = React.useState(palette);

    // Theme for ThemeProvider Component
    let theme = createTheme({
        palette: {
            primary: {
                main: getColorPalette['primary'], // buttons, sliders
            },
            secondary: {
                main: getColorPalette['secondary'],
            },
            info: {
                main: getColorPalette['primary'],
            },
            error: {
                main: '#d32f2f'
            },
            success: {
                main: '#2e7d32'
            },
            warning: {
                main: '#ed6c02'
            }
        },
    });

    return (
        <React.Fragment>
            <ThemeProvider theme={theme}>
                <NavigationBar/>
                <EditChatbot/>
            </ThemeProvider>
        </React.Fragment>
    );

}

export default MainPage;
