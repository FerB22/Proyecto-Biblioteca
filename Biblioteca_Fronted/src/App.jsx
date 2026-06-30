import React, { useState } from 'react';

function App() {
  // Estados para capturar las credenciales que escribe el usuario
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (e) => {
    e.preventDefault();
    
    // Simulación temporal de login mientras tus compañeros levantan el Gateway
    if (username === 'admin' && password === '1234') {
      alert('¡Inicio de sesión simula exitoso! Bienvenido al sistema.');
    } else {
      alert('Credenciales de prueba incorrectas. Intenta con usuario: admin y clave: 1234');
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.loginBox}>
        {/* Encabezado con el estilo de la barra superior de VivoDuoc */}
        <div style={styles.header}>
          <h2 style={styles.logoText}>
            <span style={styles.logoTextYellow}>vivo</span>Duoc
          </h2>
        </div>
        
        <div style={styles.formContainer}>
          <h3 style={styles.title}>Sistema de Biblioteca</h3>
          <p style={styles.subtitle}>Inicia sesión con tu cuenta institucional</p>
          
          <form onSubmit={handleLogin} style={styles.form}>
            <div style={styles.inputGroup}>
              <label style={styles.label}>Usuario / RUT</label>
              <input 
                type="text" 
                placeholder="Ej: admin" 
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                style={styles.input}
                required
              />
            </div>

            <div style={styles.inputGroup}>
              <label style={styles.label}>Contraseña</label>
              <input 
                type="password" 
                placeholder="••••••••" 
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                style={styles.input}
                required
              />
            </div>

            {/* Botón con el color azul oscuro institucional */}
            <button type="submit" style={styles.button}>
              Cerrar Sesión / Ingresar
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

// Metodología de estilos basados exactamente en la paleta de colores de VivoDuoc
const styles = {
  container: { 
    display: 'flex', 
    justifyContent: 'center', 
    alignItems: 'center', 
    height: '100vh', 
    width: '100vw', 
    backgroundColor: '#f4f6f9', // Fondo gris claro sutil del portal
    fontFamily: 'Arial, sans-serif', 
    position: 'fixed', 
    top: 0, 
    left: 0 
  },
  loginBox: { 
    width: '100%', 
    maxWidth: '400px', 
    backgroundColor: '#ffffff', 
    borderRadius: '8px', 
    boxShadow: '0 4px 15px rgba(0,0,0,0.08)', 
    overflow: 'hidden' 
  },
  header: {
    backgroundColor: '#002f56', // AZUL OSCURO INSTITUCIONAL DUOC
    padding: '20px',
    textAlign: 'left',
    borderBottom: '4px solid #ffb81c' // LÍNEA AMARILLA/ORO DE DUOC
  },
  logoText: {
    margin: 0,
    color: '#ffffff',
    fontSize: '24px',
    fontWeight: 'bold',
    letterSpacing: '0.5px'
  },
  logoTextYellow: {
    color: '#ffb81c' // AMARILLO VIVODUOC
  },
  formContainer: {
    padding: '30px 40px 40px 40px'
  },
  title: { 
    margin: '0 0 5px 0', 
    color: '#002f56', // Título en azul institucional
    fontSize: '20px',
    fontWeight: 'bold'
  },
  subtitle: { 
    margin: '0 0 25px 0', 
    color: '#666', 
    fontSize: '13px' 
  },
  form: { 
    display: 'flex', 
    flexDirection: 'column', 
    textAlign: 'left' 
  },
  inputGroup: { 
    marginBottom: '20px' 
  },
  label: { 
    display: 'block', 
    marginBottom: '6px', 
    color: '#333', 
    fontSize: '14px', 
    fontWeight: 'bold' 
  },
  input: { 
    width: '100%', 
    padding: '11px', 
    borderRadius: '4px', 
    border: '1px solid #ccd1d9', 
    boxSizing: 'border-box', 
    fontSize: '14px',
    outline: 'none'
  },
  button: { 
    width: '100%', 
    padding: '12px', 
    backgroundColor: '#002f56', // Botón Azul Duoc
    color: '#ffffff', 
    border: 'none', 
    borderRadius: '4px', 
    fontSize: '15px', 
    fontWeight: 'bold', 
    cursor: 'pointer', 
    marginTop: '10px',
    transition: 'background-color 0.2s',
    boxShadow: '0 2px 4px rgba(0, 47, 86, 0.2)'
  }
};

export default App;
