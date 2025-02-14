import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './MuseumExhibitionUpload.css';

const MuseumExhibitionUpload = () => {
    const [exhibitionId, setExhibitionId] = useState('');
    const [fileName, setFileName] = useState('');
    const [description, setDescription] = useState('');
    const [uploadDate, setUploadDate] = useState('');
    const [location, setLocation] = useState('');
    const [artist, setArtist] = useState('');
    const [fileInput, setFileInput] = useState(null);
    const fileInputRef = useRef(null);
    const navigate = useNavigate();
    const AUTHORIZATION_TOKEN = process.env.REACT_APP_AUTHORIZATION_TOKEN;
    const HOST = process.env.REACT_APP_HOST;
    const PORT = process.env.REACT_APP_PORT;
    const BASE_PATH = process.env.REACT_APP_PHOTOS_BASE_PATH;

    //Ensure that the call to this page comes from the login page
    useEffect(() => {
        const sessionToken = localStorage.getItem('sessionToken');
        if (!sessionToken) {
            navigate('/');
        }
    }, [navigate]);

    //Make a timeout of 5 minutes
    useEffect(() => {
        const timeoutId = setTimeout(() => {
            logoutUser();
        }, 300000);

        return () => clearTimeout(timeoutId);
    }, []);

    const logoutUser = () => {
        localStorage.removeItem('sessionToken');
        navigate('/');
    };

    //Upload the photo to the database
    const submitForm = async () => {
        try {
            const formData = new FormData();
            formData.append('exhibitionId', exhibitionId);
            formData.append('fileName', fileName);
            formData.append('description', description);
            formData.append('uploadDate', uploadDate);
            formData.append('location', location);
            formData.append('artist', artist);
            formData.append('fileInput', fileInput);

            const response = await axios.post(`http://${HOST}:${PORT}${BASE_PATH}/upload`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `${AUTHORIZATION_TOKEN}`
                }
            });
            console.log('API Response:', response.data);
            alert(response.data.returnMessage);

            setExhibitionId('');
            setFileName('');
            setDescription('');
            setUploadDate('');
            setLocation('');
            setArtist('');
            setFileInput(null);
            fileInputRef.current && (fileInputRef.current.value= '');

        } catch (error) {
            console.error('API Error:', error);
            alert(error);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        await submitForm();
    };

    return (
        <div className="reset-password-container">
            <form id="uploadForm" onSubmit={handleSubmit}>
                <header>
                    <h1>Museum Exhibition Upload</h1>
                </header>

                <label htmlFor="exhibitionId">Exhibition ID:</label>
                <input
                    type="text"
                    id="exhibitionId"
                    name="exhibitionId"
                    value={exhibitionId}
                    onChange={e => setExhibitionId(e.target.value)}
                    required
                />

                <label htmlFor="fileName">File Name:</label>
                <input
                    type="text"
                    id="fileName"
                    name="fileName"
                    value={fileName}
                    onChange={e => setFileName(e.target.value)}
                    required
                />

                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    name="description"
                    value={description}
                    onChange={e => setDescription(e.target.value)}
                    rows="4"
                    required
                ></textarea>

                <label htmlFor="uploadDate">Upload Date:</label>
                <input
                    type="date"
                    id="uploadDate"
                    name="uploadDate"
                    value={uploadDate}
                    onChange={e => setUploadDate(e.target.value)}
                    required
                />

                <label htmlFor="location">Location:</label>
                <input
                    type="text"
                    id="location"
                    name="location"
                    value={location}
                    onChange={e => setLocation(e.target.value)}
                    required
                />

                <label htmlFor="artist">Artist:</label>
                <input
                    type="text"
                    id="artist"
                    name="artist"
                    value={artist}
                    onChange={e => setArtist(e.target.value)}
                    required
                />

                <label htmlFor="fileInput">Choose File:</label>
                <input
                    type="file"
                    id="fileInput"
                    name="fileInput"
                    accept="image/*"
                    onChange={e => setFileInput(e.target.files?.[0])}
                    ref={fileInputRef}
                    required
                />

                <button type="submit">Submit</button>

                <footer>
                    &copy; Bar Yaron & Yakir Zafrani
                </footer>
            </form>
        </div>
    );
};

export default MuseumExhibitionUpload;
