import request from "./request";

function analyzePost(userInput: string) {
    return request({
        url: "/analyze",
        method: "POST",
        data: userInput,
    });
}

function uploadFile(file) {
    return request({
        url: "/uploadFile",
        method: "POST",
        data: file,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
}
const ChatbotService = {
    analyzePost,
    uploadFile
};


export default ChatbotService;
