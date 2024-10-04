import { Helmet } from 'react-helmet-async';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';
import { CONFIG } from 'src/config-global';
import { Navigate } from 'react-router-dom';
import { UserView } from 'src/sections/user/view';

// ----------------------------------------------------------------------

export default function Page() {
  const [isAuthorized, setIsAuthorized] = useState<boolean | null>(null);

  useEffect(() => {
    const role = Cookies.get('role');

    const allowedRoles = ['admin'];

    if (role) {
      setIsAuthorized(allowedRoles.includes(role.toLowerCase()));
    } else {
      setIsAuthorized(false);
    }
  }, []);

  if (isAuthorized === null) {
    return null;
  }
  if (!isAuthorized) {
    return <Navigate to="/404" replace />;
  }
  return (
    <>
      <Helmet>
        <title> {`Users - ${CONFIG.appName}`}</title>
      </Helmet>

      <UserView />
    </>
  );
}
