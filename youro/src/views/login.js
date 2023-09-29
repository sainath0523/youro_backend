import React,{useState} from "react";
import "../styles/login.css";
import { useForm } from "react-hook-form";
import { ToastContainer, toast } from 'react-toastify';
import axios from "axios";

const Login= () =>
{
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm();

  const onSubmit = (data) =>{
    toast.error('Oops!! Wrong credentials');
    // axios.post("", data).then((res) => {
    //     toast.success("Successful login")
    // }).catch((res) => {
    //     toast.error('Oops!! Wrong credentials')
    // })
  }
   
   return (
           <div class="Signupfamilymember-container">
            <ToastContainer />
             <h1>youro</h1>
             <h2>Login</h2>
             <div className="Login-Form-Container">
                <div>
                <label>Email</label>
                        <input className="input-field input-border" type="text" {...register("email", {
                                  required: true,
                                  maxLength: 32,
                                  pattern: /([-!#-'*+/-9=?A-Z^-~]+(\.[-!#-'*+/-9=?A-Z^-~]+)*|"([]!#-[^-~ \t]|(\\[\t -~]))+")@[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?(\.[0-9A-Za-z]([0-9A-Za-z-]{0,61}[0-9A-Za-z])?)+/
                                })} />
                                {errors?.email?.type === "required" && <p className="error-text">This field is required</p>}
                                {errors?.email?.type === "maxLength" && <p className="error-text">Email cannot exceed 32 characters</p>}
                                {errors?.email?.type === "pattern" && <p className="error-text">Please enter valid email</p>}
                </div>
                <div>
                <label >Password</label>
                       <input className="input-field input-border" type="password" {...register("password", {
                                  required: true,
                                  maxLength: 32,
                                  minLength: 8
                                })} ></input>
                        {errors?.password?.type === "required" && <p className="error-text">This field is required</p>}
                        {errors?.password?.type === "maxLength" && <p className="error-text">Password cannot exceed 32 characters</p>}
                </div>
                <div>
                <p className="color-secondary" style={{textDecoration: 'underline', cursor: 'pointer'}}>Forgot password?</p>
                <div className="btn-filled" style={{marginTop: '40px'}} onClick={handleSubmit(onSubmit)}>Next</div>
                </div>

             </div>
             
        </div>
    )
}

export default Login;
