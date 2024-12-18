import logo from './logo.svg';
import './App.css';
import TopNav from './component/TopNav';
import AppRoutes from './AppRoutes';



function App() {
  return (
    <div className="App">
      <header>
        <div style ={{minHeight:"10vh", width:'100%', backgroundColor:'green'}} ></div>
        <TopNav />
      </header>
      <main>
        <div className='main-body' style={{backgroundImage: `url('https://media-private.canva.com/Gic-w/MAGZdmGic-w/1/p.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAJWF6QO3UH4PAAJ6Q%2F20241216%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241216T124042Z&X-Amz-Expires=21575&X-Amz-Signature=eaf76d2c6f344c450dae87a4ba28e547f7bad51dc303b3a5c89c619cca66aef9&X-Amz-SignedHeaders=host%3Bx-amz-expected-bucket-owner&response-expires=Mon%2C%2016%20Dec%202024%2018%3A40%3A17%20GMT')`,backgroundSize: 'cover',backgroundPosition: 'center', minHeight:'80vh' }}>
          <AppRoutes/>
        </div>
      </main>
      
      <footer>
          <div style ={{minHeight:"10vh", width:'100%', backgroundColor:'green'}}></div>
      </footer>
    </div>
  );
}

export default App;