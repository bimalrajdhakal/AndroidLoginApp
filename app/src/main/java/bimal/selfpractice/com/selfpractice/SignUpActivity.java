package bimal.selfpractice.com.selfpractice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG="SignupActivity";

    @BindView(R.id.input_name)EditText _nameText;
    @BindView(R.id.input_address)EditText _addressText;
    @BindView(R.id.input_email)EditText _emailText;
    @BindView(R.id.input_mobile)EditText _mobileText;
    @BindView(R.id.input_password)EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)Button _signupButton;
    @BindView(R.id.link_login)TextView _loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the registration screen and return to the Login Activity
                Intent intent=new Intent(getApplicationContext(),Login_activity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
            }
        });

    }

    // signup method

    public void signup(){
        Log.d(TAG,"Signup");
        if(!validate()){
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account");
        progressDialog.show();

        String name=_nameText.getText().toString();
        String address=_addressText.getText().toString();
        String email=_emailText.getText().toString();
        String mobile=_mobileText.getText().toString();
        String password=_passwordText.getText().toString();
        String reEnterPassword=_reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        // on complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        //onSignupFailed();
                        progressDialog.dismiss();
                    }
                },3000);

    }

    public void onSignupSuccess(){
        _signupButton.setEnabled(true);
        setResult(RESULT_OK,null);
        finish();
    }

    public void onSignupFailed(){
        Toast.makeText(getBaseContext(),"Signup Failed",Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate(){
        boolean valid=true;

        String name=_nameText.getText().toString();
        String address=_addressText.getText().toString();
        String email=_emailText.getText().toString();
        String mobile=_mobileText.getText().toString();
        String password=_passwordText.getText().toString();
        String reEnterPassword=_reEnterPasswordText.getText().toString();

        // name validation
        if(name.isEmpty()||name.length()<3){
            _nameText.setError("Name should be at least 3 characters");
            valid=false;
        }else{
            _nameText.setError(null);
        }

        // address validation
        if(address.isEmpty()){
            _addressText.setError("Enter valid Address");
            valid=false;
        }else{
            _addressText.setError(null);
        }

        //email validation
        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _emailText.setError("Enter a valid email address");
            valid=false;
        }else{
            _emailText.setError(null);
        }

        //mobile validation
        if(mobile.isEmpty()||mobile.length()!=10){
            _mobileText.setError("Enter Valid Mobile Number");
            valid=false;
        }else{
            _mobileText.setError(null);
        }

        // password validation

        if(password.isEmpty()||password.length()<4||password.length()>10){
            _passwordText.setError("Password should betwwen 4 and 10 alphanumeric characters");
            valid=false;
        }else{
            _passwordText.setError(null);
        }

        // confirm password validation

        if(reEnterPassword.isEmpty()|| !reEnterPassword.equals(password)){
            _reEnterPasswordText.setError("Password Do not match");
            valid=false;
        }else{
            _reEnterPasswordText.setError(null);
        }

        return valid;

    }
}
