import request from "./request";

function analyzePost(userInput: string) {
    return request({
        url: "/analyze",
        method: "POST",
        data: userInput,
    });
}

const ChatbotService = {
    analyzePost,
};


export default ChatbotService;
