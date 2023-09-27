import React,{useState} from "react";
// import "../styles/Doctor-Signup.css"

const DoctorSignup= () =>
{

  const [firstname, setfirstname] = useState('');
  const [lastname, setlastname] = useState('');
  const [email, setEmail] = useState('');
  const [createpassword, setpassword] = useState('');
  const [retypepassword, setretypepassword] = useState('');
  const [licensenumber, setlicensenumber] = useState('');
  const [addressline1, setaddressline1] = useState('');
  const [city, setcity] = useState('');  
  // Function to handle input changes and update the state
  const handleFirstNameChange = (event) => {
    setfirstname(event.target.value);
  };
  
   const handleLastNameChange = (event) => {
    setlastname(event.target.value);
  };

   const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };


 const handlePasswordChange = (event) => {
    setpassword(event.target.value);
  };

 const handleRetypePasswordChange = (event) => {
    setretypepassword(event.target.value);
  };

const handleMedicalInsurance = (event) => {
   setlicensenumber(event.target.value);
  };

const handleRelationtoFamilyMember =(event) => {
   setaddressline1(event.target.value);
}

const handleRelationEmail = (event) => {
   setcity(event.target.value);
}
   
   return (
           <div class="doctorsignup-container">
             <h1>youro</h1>
             <h4>Membership/Sign-up gives you direct access to our team of<br/> urological providers with same or next day appointments.  </h4>
               <div className="Form-Container">
                     <div className="name-fields">
                          <div className="label-input">
                               <label>First Name</label>
                               <input className="input-field" type="text" value={firstname} onChange={handleFirstNameChange} />
                          </div>

    		     <div className="label-input">
                         <label>Last Name</label>
                        <input className="input-field" type="text" value={lastname} onChange={handleLastNameChange} />
                     </div>
                </div>

                    <div className="name-fields">
                       <div className="label-input">
                        <label>Email </label>
                        <input className="input-field" type="text" value={email} onChange={handleEmailChange}/>
                    </div>

                   <div className="label-input">
                        <label>Phone Number</label>
                        <input className="input-field" type="text" value={licensenumber} onChange={handleMedicalInsurance}/>
                   </div>
                   </div>

                  <div className="name-fields">
                    <div className="label-input">
                       <label>License Number</label>
                        <input className="input-field" type="text" value={addressline1} onChange={handleRelationtoFamilyMember}/>
                    </div>

                     <div className="label-input">
                       <label>Clinic Address Line 1</label>
                        <input className="input-field" type="text" value={city} onChange={handleRelationEmail}/>
                    </div> 
                  </div>

                  <div className="name-fields">
                    <div className="label-input">
                       <label>City</label>
                        <input className="input-field" type="text" value={addressline1} onChange={handleRelationtoFamilyMember}/>
                    </div>

                     <div className="label-input">
                       <label>Pincode</label>
                        <input className="input-field" type="text" value={city} onChange={handleRelationEmail}/>
                    </div> 
                  </div> 

                   <div className="name-fields">
                    <div className="label-input">
                     <label >Create Password</label>
                       <input className="input-field" type="text" value={createpassword} onChange={handlePasswordChange} ></input>
	                  </div>
                    <div className="label-input">
                     <label >Re-type Password</label>
                   <input className="input-field" type="password" value={retypepassword} onChange={handleRetypePasswordChange} ></input>
                   </div>
                   </div> 

                    
                    <div class="container">
                       <p>This is some content in the container.</p>
                        <button class="next-button">Next</button>
                    </div>

                     <div className="login-label">
                     <p>Already youro member? Login </p>
	                  </div>
        </div>
        </div>
    )
}

export default DoctorSignup;
