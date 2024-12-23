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
        <div className='main-body' style={{backgroundImage: `url('https://cdn.pixabay.com/photo/2020/09/01/15/05/aircraft-cabin-5535467_1280.jpg')`,backgroundSize: 'cover',backgroundPosition: 'center', minHeight:'80vh' }}>
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