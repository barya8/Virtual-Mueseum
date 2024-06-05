import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Login from './components/Login';
import ResetPassword from './components/ResetPassword';
import MuseumExhibitionUpload from './components/MuseumExhibitionUpload';

const App = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={<Login/>} />
                <Route path="/reset-password" element={<ResetPassword/>} />
                <Route path="/museum-exhibition-upload" element={<MuseumExhibitionUpload/>} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;
