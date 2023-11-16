// react
import React from "react";

import {ThemeProvider, createTheme} from '@mui/material/styles';
import NavigationBar from "../../NavigationBar/NavigationBar";
import CustomChatbot from "../../components/EditChatbot/CustomChatbot";
//custom



// default colors
const palette = {
    primary: '#008587'
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
            },
        },
    });

    return (
        <>
            <ThemeProvider theme={theme}>
                <NavigationBar/>
            </ThemeProvider>
            <CustomChatbot/>
        </>
    );

}

export default MainPage;
