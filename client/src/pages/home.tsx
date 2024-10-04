import { Helmet } from 'react-helmet-async';
import Cookies from 'js-cookie';
import { useEffect, useState } from 'react';
import { CONFIG } from 'src/config-global';
import { Navigate } from 'react-router-dom';
import { OverviewAnalyticsView } from 'src/sections/overview/view';

// ----------------------------------------------------------------------

export default function Page() {
  const [isAuthorized, setIsAuthorized] = useState<boolean | null>(null);

  useEffect(() => {
    const role = Cookies.get('role');

    const allowedRoles = ['admin', 'operations'];

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
        <title> {`Dashboard - ${CONFIG.appName}`}</title>
        <meta
          name="description"
          content="The starting point for your next project with Minimal UI Kit, built on the newest version of Material-UI ©, ready to be customized to your style"
        />
        <meta name="keywords" content="react,material,kit,application,dashboard,admin,template" />
      </Helmet>

      <OverviewAnalyticsView />
    </>
  );
}
