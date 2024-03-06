import React, { useState } from "react";
//mui
import Snackbar from "@mui/material/Snackbar";
import Alert from '@mui/material/Alert';
// custom
import NavigationBar from "../../NavigationBar/NavigationBar";
import CustomChatbot from "../../components/EditChatbot/CustomChatbot";

// default colors
const themeChatbot = {
    fontFamily: 'Arial, sans-serif',
    primary: '#002C34',
    headerFontSize: '15px',
    white: '#fff',
    secondary: '#4a4a4a',
    error: '#F0B2B2'
};

/**
 * Hauptseite-Komponente
 */
const MainPage = () => {
    const [showPopUp, setShowPopUp] = useState(false);
    const [popUpMessage, setPopUpMessage] = useState("");
    const [colorPopUp, setColorPopUp] = useState("");

    return (<>
            <NavigationBar theme={themeChatbot}/>
            <CustomChatbot theme={themeChatbot} setShowPopUp={setShowPopUp} setPopUpMessage={setPopUpMessage} setColorPopUp={setColorPopUp} />
            <Snackbar
                open={showPopUp}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                autoHideDuration={3000} // 3 Sekunden
                onClose={() => { setShowPopUp(false) }}
            >
                <Alert
                    severity={colorPopUp}
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
