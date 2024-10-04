import { useState, useCallback } from 'react';
import axios from 'axios';

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import LoadingButton from '@mui/lab/LoadingButton';
import InputAdornment from '@mui/material/InputAdornment';

import { useRouter } from 'src/routes/hooks';

import { Iconify } from 'src/components/iconify';

// ----------------------------------------------------------------------

export function SignInView() {
  const [signUp, setSignUp] = useState(false);
  const router = useRouter();

  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const [formValues, setFormValues] = useState({
    name: '',
    email: '',
    password: '',
  });
  const [formErrors, setFormErrors] = useState({
    name: false,
    email: false,
    password: false,
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };
  const validateForm = useCallback(() => {
    const errors = {
      name: signUp && !formValues.name, // Check name only when signUp
      email: !formValues.email,
      password: !formValues.password,
    };
    setFormErrors(errors);

    // Return true only if all fields are valid
    return !errors.name && !errors.email && !errors.password;
  }, [formValues, signUp]);
  const handleSubmit = useCallback(async () => {
    const isValid = validateForm();
    if (isValid) {
      setLoading(true);
      let response = null;

      try {
        if (signUp) {
          // Sign Up API call
          response = await axios.post(
            'http://localhost:9000/api/signup',
            {
              name: formValues.name,
              email: formValues.email,
              password: formValues.password,
            },
            { withCredentials: true }
          );
        } else {
          // Sign In API call
          response = await axios.post(
            'http://localhost:9000/api/signin',
            {
              email: formValues.email,
              password: formValues.password,
            },
            { withCredentials: true }
          );
        }

        if (response && response.status === 200) {
          router.push('/'); // Redirect to the homepage on successful response
        }
      } catch (error: any) {
        console.error(
          `Failed to ${signUp ? 'sign up' : 'sign in'}`,
          error.response?.data || error.message
        );
      } finally {
        setLoading(false);
      }
    }
  }, [formValues, signUp, router, validateForm]);

  const renderForm = (
    <Box display="flex" flexDirection="column" alignItems="flex-end">
      {signUp && (
        <TextField
          fullWidth
          name="name"
          label="Full name"
          placeholder="John Doe"
          InputLabelProps={{ shrink: true }}
          sx={{ mb: 3 }}
          error={formErrors.name}
          helperText={formErrors.name ? 'Full name is required' : ''}
          value={formValues.name}
          onChange={handleInputChange}
        />
      )}
      <TextField
        fullWidth
        name="email"
        label="Email address"
        placeholder="hello@gmail.com"
        InputLabelProps={{ shrink: true }}
        sx={{ mb: 3 }}
        error={formErrors.email}
        helperText={formErrors.email ? 'Email address is required' : ''}
        value={formValues.email}
        onChange={handleInputChange}
      />

      <Link variant="body2" color="inherit" sx={{ mb: 1.5, cursor: 'pointer' }}>
        Forgot password?
      </Link>

      <TextField
        fullWidth
        name="password"
        label="Password"
        placeholder="********"
        InputLabelProps={{ shrink: true }}
        type={showPassword ? 'text' : 'password'}
        InputProps={{
          endAdornment: (
            <InputAdornment position="end">
              <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                <Iconify icon={showPassword ? 'solar:eye-bold' : 'solar:eye-closed-bold'} />
              </IconButton>
            </InputAdornment>
          ),
        }}
        sx={{ mb: 3 }}
        error={formErrors.password}
        helperText={formErrors.password ? 'Password is required' : ''}
        value={formValues.password}
        onChange={handleInputChange}
      />

      <LoadingButton
        fullWidth
        size="large"
        type="submit"
        color="inherit"
        variant="contained"
        onClick={handleSubmit}
        loading={loading}
      >
        {signUp ? 'Sign up' : 'Sign in'}
      </LoadingButton>
    </Box>
  );

  return (
    <>
      <Box gap={1.5} display="flex" flexDirection="column" alignItems="center" sx={{ mb: 5 }}>
        <Typography variant="h5">{signUp ? 'Sign up' : 'Sign in'}</Typography>
        <Typography variant="body2" color="text.secondary">
          {signUp ? 'Already have an account?' : 'Don’t have an account?'}
          {signUp ? (
            <Link
              onClick={() => setSignUp(false)}
              variant="subtitle2"
              sx={{ ml: 0.5, cursor: 'pointer' }}
            >
              Sign in
            </Link>
          ) : (
            <Link
              onClick={() => setSignUp(true)}
              variant="subtitle2"
              sx={{ ml: 0.5, cursor: 'pointer' }}
            >
              Get started
            </Link>
          )}
        </Typography>
      </Box>

      {renderForm}
    </>
  );
}
