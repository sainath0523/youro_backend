import './App.css';
import DoctorSignup from './views/Doctor-Signup';
import SignupforFamilyMember from './views/Signup-For-FamilyMember';
import Signupformyself from './views/Signup-For-Myself';
import Signupoptions from './views/Signupoptions';
import Patientaddress from './views/PatientAddress';
import Login from './views/login';

import { BrowserRouter as Router,Routes, Route, Link } from 'react-router-dom';
import AdminSignup from './views/Admin-Signup';
import PrivacyPolicy from './views/PrivacyPolicies';
import { ToastContainer, toast } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

function App() {
  return ( 
    <>
     <Router>
     <Routes>
        <Route path='login' element={<Login/>}></Route>
        <Route path='/signup' element={<Signupoptions/>}></Route>
        <Route path='/signupmyself' element={<Signupformyself/>}></Route>
        <Route path='/signupforfamilymember' element={<SignupforFamilyMember/>}></Route>
        <Route path='/signupprovider' element={<DoctorSignup/>}></Route>
        <Route path='/adminsignup' element={<AdminSignup/>}></Route>
        <Route path='/pp' element={<PrivacyPolicy/>}></Route>
     </Routes>
    </Router>
    
    </>
  );
}

export default App;
