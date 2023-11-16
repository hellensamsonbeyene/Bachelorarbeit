const initialState = {
  data: null,
  loading: false,
  error: null,
};

const apiReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'API_REQUEST':
      return { ...state, loading: true };
    case 'API_SUCCESS':
      return { ...state, loading: false, data: action.payload, error: null };
    case 'API_FAILURE':
      return { ...state, loading: false, data: null, error: action.payload };
    default:
      return state;
  }
};

export default apiReducer;
