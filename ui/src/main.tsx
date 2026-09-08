import React from 'react';
import ReactDOM from 'react-dom/client';

const App = () => (
  <div style={{ fontFamily: 'sans-serif', padding: '20px' }}>
    <h1>Concurrency Labs Simulator</h1>
    <p>Status: React UI skeleton loaded successfully!</p>
  </div>
);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);