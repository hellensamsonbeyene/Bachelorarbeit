// react
import React, {useState} from "react";

import {ThemeProvider, createTheme} from '@mui/material/styles';
import NavigationBar from "../../NavigationBar/NavigationBar";
import CustomChatbot from "../../components/EditChatbot/CustomChatbot";
import Snackbar from "@mui/material/Snackbar";
import Alert from '@mui/material/Alert';

//custom



// default colors
const palette = {
    primary: '#008587'
}

const MainPage = () => {

      // eslint-disable-next-line
    const [getColorPalette, setColorPalette] = React.useState(palette);
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [colorPopUp, setColorPopUp] = useState("");

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
            <CustomChatbot setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage} setColorPopUp={setColorPopUp} />
            <Snackbar
                open={showPopUp}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                autoHideDuration={3000} // 3000 milliseconds (3 seconds)
                onClose={() => {setShowPopUp(false)}}
            >
                <Alert
                    severity={colorPopUp} // Make sure colorPopUp is "success"
                    variant="filled"
                    sx={{ width: '100%' }}
                >
                    {popUpMessage}
                </Alert>
            </Snackbar>
        </>
    );

}

export default MainPage;
