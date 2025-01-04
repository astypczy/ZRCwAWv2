import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {Amplify} from "aws-amplify";

Amplify.configure({
  Auth: {
    Cognito: {
      userPoolId: 'us-east-1_OMPIqfVaW',
      userPoolClientId: '59idbtnj82biv9ii6n6n8rhb2t'
    }
  }
});
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
