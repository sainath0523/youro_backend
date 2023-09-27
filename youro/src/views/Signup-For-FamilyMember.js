import React,{useState} from "react";
import "../styles/Signup-For-FamilyMember.css"

const SignupforFamilyMember= () =>
{

  const [firstname, setfirstname] = useState('');
  const [lastname, setlastname] = useState('');
  const [email, setEmail] = useState('');
  const [createpassword, setpassword] = useState('');
  const [retypepassword, setretypepassword] = useState('');
  const [medicalinsurance, setmedicalinsurance] = useState('');
  const [relationtofamilymember, setrelationtofamilymember] = useState('');
  const [relationemail, setrelationemail] = useState('');  
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
   setmedicalinsurance(event.target.value);
  };

const handleRelationtoFamilyMember =(event) => {
   setrelationtofamilymember(event.target.value);
}

const handleRelationEmail = (event) => {
   setrelationemail(event.target.value);
}
   
   return (
           <div class="Signupfamilymember-container">
             <h1>youro</h1>
             <h4>Membership/Sign-up gives you direct access to our team of<br/> urological providers with same or next day appointments.  </h4>
               <div className="Form-Container">
                     <div className="name-fields">
                          <div className="label-input">
                               <label>Family Member First Name</label>
                               <input className="input-field" type="text" value={firstname} onChange={handleFirstNameChange} />
                          </div>

    		     <div className="label-input">
                         <label>Family Member Last Name</label>
                        <input className="input-field" type="text" value={lastname} onChange={handleLastNameChange} />
                     </div>
                </div>

                    <div className="name-fields">
                       <div className="label-input">
                        <label>Email (Family Member)</label>
                        <input className="input-field" type="text" value={email} onChange={handleEmailChange}/>
                    </div>

                   <div className="label-input">
                        <label>Do you have medical Insurance?</label>
                        <input className="input-field" type="text" value={medicalinsurance} onChange={handleMedicalInsurance}/>
                   </div>
                   </div>

                  <div className="name-fields">
                    <div className="label-input">
                       <label>How your related to family Member</label>
                        <input className="input-field" type="text" value={relationtofamilymember} onChange={handleRelationtoFamilyMember}/>
                    </div>

                     <div className="label-input">
                       <label>Email (email of the member who is registering on behalf of family member)</label>
                        <input className="input-field" type="text" value={relationemail} onChange={handleRelationEmail}/>
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
                    
                   <button className="next-button">Next</button>

                   
        </div>
        </div>
    )
}

export default SignupforFamilyMember;



//  <div className="bottom-container">
//                     <p>Already youro member? <span>Login</span></p>
//                     </div>
//                    <button className="next-button">Next</button>

// <div className="name-fields">
//                <div className="firstname-container">
//                  <label >First Name</label>
//                  <input id="input-field1" type="text" value={firstname} onChange={handleFirstNameChange} ></input>
// 	         </div>
//                  <div className="lastname-container">
//                  <label >Last Name</label>
//                  <input id="input-field1" type="text" value={lastname} onChange={handleLastNameChange} ></input>
//                  </div>
//                  </div>

//                 <div className="name-fields">
//                   <div className="email-container">
//                  <label >Email (Family Member)</label>
//                  <input id="input-field2" type="text" value={firstname} onChange={handleFirstNameChange} ></input>
// 	         </div>
//                  <div className="firstname-container">
//                  <label >Do you have medical Insurance ?</label>
//                  <input id="input-field3" type="text" value={lastname} onChange={handleLastNameChange} ></input>
//                  </div>
//                  </div>

//                  <div className="name-fields">
//                <div className="firstname-container">
//                  <label >First Name</label>
//                  <input id="input-field1" type="text" value={firstname} onChange={handleFirstNameChange} ></input>
// 	         </div>
//                  <div className="lastname-container">
//                  <label >Last Name</label>
//                  <input id="input-field1" type="text" value={lastname} onChange={handleLastNameChange} ></input>
//                  </div>
//                  </div>

//                  <div className="name-fields">
//                <div className="firstname-container">
//                  <label >First Name</label>
//                  <input id="input-field1" type="text" value={firstname} onChange={handleFirstNameChange} ></input>
// 	         </div>
//                  <div className="lastname-container">
//                  <label >Last Name</label>
//                  <input id="input-field1" type="text" value={lastname} onChange={handleLastNameChange} ></input>
//                  </div>
//                  </div>