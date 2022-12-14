// Generated by view binder compiler. Do not edit!
package com.murfy.groupify.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.murfy.groupify.R;
import com.murfy.groupify.customElements.CustomEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView accountinfo;

  @NonNull
  public final TextView accountinfoError;

  @NonNull
  public final TextView alreadyHaveAccount;

  @NonNull
  public final EditText emailInput;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final ImageView loginBackground;

  @NonNull
  public final TextView loginInstead;

  @NonNull
  public final CustomEditText passwordInput;

  @NonNull
  public final TextView pickusername;

  @NonNull
  public final Button signup;

  @NonNull
  public final TextView usernameError;

  @NonNull
  public final EditText usernameInput;

  private ActivitySignupBinding(@NonNull ConstraintLayout rootView, @NonNull TextView accountinfo,
      @NonNull TextView accountinfoError, @NonNull TextView alreadyHaveAccount,
      @NonNull EditText emailInput, @NonNull LinearLayout linearLayout,
      @NonNull LinearLayout linearLayout2, @NonNull ImageView loginBackground,
      @NonNull TextView loginInstead, @NonNull CustomEditText passwordInput,
      @NonNull TextView pickusername, @NonNull Button signup, @NonNull TextView usernameError,
      @NonNull EditText usernameInput) {
    this.rootView = rootView;
    this.accountinfo = accountinfo;
    this.accountinfoError = accountinfoError;
    this.alreadyHaveAccount = alreadyHaveAccount;
    this.emailInput = emailInput;
    this.linearLayout = linearLayout;
    this.linearLayout2 = linearLayout2;
    this.loginBackground = loginBackground;
    this.loginInstead = loginInstead;
    this.passwordInput = passwordInput;
    this.pickusername = pickusername;
    this.signup = signup;
    this.usernameError = usernameError;
    this.usernameInput = usernameInput;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.accountinfo;
      TextView accountinfo = ViewBindings.findChildViewById(rootView, id);
      if (accountinfo == null) {
        break missingId;
      }

      id = R.id.accountinfoError;
      TextView accountinfoError = ViewBindings.findChildViewById(rootView, id);
      if (accountinfoError == null) {
        break missingId;
      }

      id = R.id.alreadyHaveAccount;
      TextView alreadyHaveAccount = ViewBindings.findChildViewById(rootView, id);
      if (alreadyHaveAccount == null) {
        break missingId;
      }

      id = R.id.emailInput;
      EditText emailInput = ViewBindings.findChildViewById(rootView, id);
      if (emailInput == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.loginBackground;
      ImageView loginBackground = ViewBindings.findChildViewById(rootView, id);
      if (loginBackground == null) {
        break missingId;
      }

      id = R.id.loginInstead;
      TextView loginInstead = ViewBindings.findChildViewById(rootView, id);
      if (loginInstead == null) {
        break missingId;
      }

      id = R.id.passwordInput;
      CustomEditText passwordInput = ViewBindings.findChildViewById(rootView, id);
      if (passwordInput == null) {
        break missingId;
      }

      id = R.id.pickusername;
      TextView pickusername = ViewBindings.findChildViewById(rootView, id);
      if (pickusername == null) {
        break missingId;
      }

      id = R.id.signup;
      Button signup = ViewBindings.findChildViewById(rootView, id);
      if (signup == null) {
        break missingId;
      }

      id = R.id.usernameError;
      TextView usernameError = ViewBindings.findChildViewById(rootView, id);
      if (usernameError == null) {
        break missingId;
      }

      id = R.id.usernameInput;
      EditText usernameInput = ViewBindings.findChildViewById(rootView, id);
      if (usernameInput == null) {
        break missingId;
      }

      return new ActivitySignupBinding((ConstraintLayout) rootView, accountinfo, accountinfoError,
          alreadyHaveAccount, emailInput, linearLayout, linearLayout2, loginBackground,
          loginInstead, passwordInput, pickusername, signup, usernameError, usernameInput);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
