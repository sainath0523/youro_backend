import './App.css';
import DoctorSignup from './views/Doctor-Signup';
import SignupforFamilyMember from './views/Signup-For-FamilyMember';
import Signupformyself from './views/Signup-For-Myself';
import Signupoptions from './views/Signupoptions';
import Login from './views/login';

import { BrowserRouter as Router,Routes, Route, Link } from 'react-router-dom';

function App() {
  return ( 
     <Router>
     <Routes>
        <Route path='login' element={<Login/>}></Route>
        <Route path='/signup' element={<Signupoptions/>}></Route>
        <Route path='/signupmyself' element={<Signupformyself/>}></Route>
        <Route path='/signupforfamilymember' element={<SignupforFamilyMember/>}></Route>
        <Route path='/signupprovider' element={<DoctorSignup/>}></Route>
     </Routes>
    </Router>
  );
}

export default App;
