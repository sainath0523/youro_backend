import './App.css';
import DoctorSignup from './views/Doctor-Signup';
import SignupforFamilyMember from './views/Signup-For-FamilyMember';
import Signupformyself from './views/Signup-For-Myself';
import Signupoptions from './views/Signupoptions';

import { BrowserRouter as Router,Routes, Route, Link } from 'react-router-dom';

function App() {
  return ( 
     <Router>
     <div className="App">
       <SignupforFamilyMember/>
    </div>
     <Routes>
        <Route path='/Login' element={<Signupoptions/>}></Route>
        <Route path='/signupmyself' element={<Signupformyself/>}></Route>
        <Route path='/signupforfamilymember' element={<SignupforFamilyMember/>}></Route>
     </Routes>
    </Router>
  );
}

export default App;
