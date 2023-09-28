import React,{useState} from "react";
import { useForm } from "react-hook-form";
import "../styles/Signup-For-FamilyMember.css";
// import "../styles/Doctor-Signup.css"

const DoctorSignup= () =>
{

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();
  
   
   return (
           <div class="Signupfamilymember-container">
             <h1>youro</h1>
             <p className="h3">Membership/Sign-up gives you direct access to our team of<br/> urological providers with same or next day appointments.  </p>
               <div className="Form-Container">
                     <div className="name-fields">
                          <div className="label-input">
                               <label>First Name</label>
                               <input className="input-field input-border" type="text" {...register("firstName", {
                                  required: true,
                                  maxLength: 32,
                                })} />
                                {errors?.firstName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.firstName?.type === "maxLength" && <p className="error-text">First name cannot exceed 32 characters</p>}
                          </div>

    		     <div className="label-input">
                         <label>Last Name</label>
                         <input className="input-field input-border" type="text" {...register("lastName", {
                                  required: true,
                                  maxLength: 32,
                                })}/>
                                {errors?.lastName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.lastName?.type === "maxLength" && <p className="error-text">Last Name cannot exceed 32 characters</p>}
                     </div>
              </div>

                    <div className="name-fields">
                       <div className="label-input">
                        <label>Email Address</label>
                        <input className="input-field input-border" type="text" {...register("email", {
                                  required: true,
                                  maxLength: 32,
                                  pattern: /([-!#-'*+/-9=?A-Z^-~]+(\.[-!#-'*+/-9=?A-Z^-~]+)*|"([]!#-[^-~ \t]|(\\[\t -~]))+")@[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?(\.[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?)+/
                                })} />
                                {errors?.email?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.email?.type === "maxLength" && <p className="error-text">Email cannot exceed 32 characters</p>}
                                {errors?.email?.type === "pattern" && <p className="error-text">Please enter valid email</p>}
                    </div>

                   <div className="label-input">
                        <label>Phone Number</label>
                        <input className="input-field input-border" type="text" {...register("phone", {
                                  required: true,
                                  maxLength: 32,
                                  pattern: /^(\+?\d{1,4}[\s-])?(?!0+\s+,?$)\d{10}\s*,?$/
                                })} />
                                {errors?.email?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.email?.type === "maxLength" && <p className="error-text">Mobile Number cannot exceed 32 characters</p>}
                                {errors?.email?.type === "pattern" && <p className="error-text">Please enter valid Mobile Number</p>}
                   </div>
                   </div>

                  <div className="name-fields">
                    <div className="label-input">
                       <label>License Number</label>
                       <input className="input-field input-border" type="text" {...register("licenseNumber", {
                                  required: true,
                                  maxLength: 32,
                                })}/>
                                {errors?.lastName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.lastName?.type === "maxLength" && <p className="error-text">License number cannot exceed 32 characters</p>}
                     </div>
                    

                     <div className="label-input">
                       <label>Clinic Address</label>
                       <input className="input-field input-border" type="text" {...register("clinicAddress", {
                                  required: true,
                                  maxLength: 64,
                                })}/>
                                {errors?.lastName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.lastName?.type === "maxLength" && <p className="error-text">Clinic Address cannot exceed 64 characters</p>}
                     
                    </div> 
                  </div>

                  <div className="name-fields">
                    <div className="label-input">
                       <label>City&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                       <input className="input-field input-border" type="text" {...register("city", {
                                  required: true,
                                  maxLength: 32,
                                })}/>
                                {errors?.lastName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.lastName?.type === "maxLength" && <p className="error-text">City cannot exceed 32 characters</p>}
                    </div>

                     <div className="label-input">
                       <label>Pincode&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                       <input className="input-field input-border" type="text" {...register("pincode", {
                                  required: true,
                                  maxLength: 32,
                                })}/>
                                {errors?.lastName?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.lastName?.type === "maxLength" && <p className="error-text">Pincode cannot exceed 32 characters</p>}
                    </div> 
                  </div> 

                  <div className="name-fields">
                    <div className="label-input">
                     <label >Create Password</label>
                       <input className="input-field input-border" type="password" {...register("password", {
                                  required: true,
                                  maxLength: 32,
                                  minLength: 8
                                })} ></input>
                        {errors?.password?.type === "required" && <p className="error-text">This field is required</p>}
                        {errors?.password?.type === "maxLength" && <p className="error-text">Password cannot exceed 32 characters</p>}
                        {errors?.password?.type === "minLength" && <p className="error-text">Password length must be more than 8 characters</p>}
	            </div>
                    <div className="label-input">
                     <label >Re-type Password</label>
                   <input className="input-field input-border" type="password" {...register("confirmPassword", {
                                  validate: val => watch('password') === val
                                })}  ></input>
                    {errors?.confirmPassword && <p className="error-text">Passwords must match</p>}
                   </div>
                   </div> 

                    
                   <div className="name-fields">
                    <div className="login-label" style={{width: "48%"}}>
                     <p>Already youro member? Login </p>
	            </div>
                    <div className="login-label" style={{width: "48%"}}>
                        <div style={{width: "85%", position: "relative"}}>
                        <div className="next-button btn-filled" onClick={handleSubmit((data) => console.log(data))}>Next</div>
                        </div>
                        
                    </div>
                </div>
        </div>
        </div>
    )
}

export default DoctorSignup;
