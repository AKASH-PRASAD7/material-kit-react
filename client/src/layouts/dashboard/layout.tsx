import type { Theme, SxProps, Breakpoint } from '@mui/material/styles';
import Cookies from 'js-cookie';
import { useState, useEffect } from 'react';

import Box from '@mui/material/Box';
import Alert from '@mui/material/Alert';
import { useTheme } from '@mui/material/styles';

import { _langs, _notifications } from 'src/_mock';
import { SvgColor } from 'src/components/svg-color';
import { Main } from './main';
import { layoutClasses } from '../classes';
import { NavMobile, NavDesktop } from './nav';
// import { navData } from '../config-nav-dashboard';

import { _workspaces } from '../config-nav-workspace';
import { MenuButton } from '../components/menu-button';
import { LayoutSection } from '../core/layout-section';
import { HeaderSection } from '../core/header-section';
import { AccountPopover } from '../components/account-popover';

// ----------------------------------------------------------------------

export type DashboardLayoutProps = {
  sx?: SxProps<Theme>;
  children: React.ReactNode;
  header?: {
    sx?: SxProps<Theme>;
  };
};

const icon = (name: string) => (
  <SvgColor width="100%" height="100%" src={`/assets/icons/navbar/${name}.svg`} />
);

// Function to generate navigation data based on the role
const getNavData = (role: string | undefined) => [
  {
    title: 'Dashboard',
    path: '/dashboard',
    icon: icon('ic-analytics'),
    show: role === 'admin' || role === 'operations',
  },
  {
    title: 'User',
    path: '/user',
    icon: icon('ic-user'),
    show: role === 'admin',
  },
  {
    title: 'Product',
    path: '/',
    icon: icon('ic-cart'),
    show: true,
  },
  {
    title: 'Blog',
    path: '/blog',
    icon: icon('ic-blog'),
    show: role === 'sales' || role === 'admin' || role === 'operations',
  },
  {
    title: 'Sign in',
    path: '/sign-in',
    icon: icon('ic-lock'),
    show: true,
  },
];

export function DashboardLayout({ sx, children, header }: DashboardLayoutProps) {
  const theme = useTheme();

  const [navOpen, setNavOpen] = useState(false);
  const [showAccountPopover, setShowAccountPopover] = useState(false);

  const layoutQuery: Breakpoint = 'lg';

  useEffect(() => {
    const nameCookie = Cookies.get('name');
    setShowAccountPopover(!!nameCookie);
  }, []);

  const [role, setRole] = useState<string | undefined>(Cookies.get('role'));
  const [navData, setNavData] = useState(getNavData(role));

  useEffect(() => {
    const checkCookies = () => {
      const newRole = Cookies.get('role');
      if (newRole !== role) {
        setRole(newRole);
        setNavData(getNavData(newRole));
      }
    };

    const interval = setInterval(checkCookies, 1000);

    return () => clearInterval(interval);
  }, [role]);

  return (
    <LayoutSection
      /** **************************************
       * Header
       *************************************** */
      headerSection={
        <HeaderSection
          layoutQuery={layoutQuery}
          slotProps={{
            container: {
              maxWidth: false,
              sx: { px: { [layoutQuery]: 5 } },
            },
          }}
          sx={header?.sx}
          slots={{
            topArea: (
              <Alert severity="info" sx={{ display: 'none', borderRadius: 0 }}>
                This is an info Alert.
              </Alert>
            ),
            leftArea: (
              <>
                <MenuButton
                  onClick={() => setNavOpen(true)}
                  sx={{
                    ml: -1,
                    [theme.breakpoints.up(layoutQuery)]: { display: 'none' },
                  }}
                />
                <NavMobile
                  data={navData}
                  open={navOpen}
                  onClose={() => setNavOpen(false)}
                  workspaces={_workspaces}
                />
              </>
            ),
            rightArea: (
              <Box gap={1} display="flex" alignItems="center">
                {showAccountPopover && (
                  <AccountPopover setShowAccountPopover={setShowAccountPopover} />
                )}
              </Box>
            ),
          }}
        />
      }
      /** **************************************
       * Sidebar
       *************************************** */
      sidebarSection={
        <NavDesktop
          data={navData}
          showLogin={!showAccountPopover}
          layoutQuery={layoutQuery}
          workspaces={_workspaces}
        />
      }
      /** **************************************
       * Footer
       *************************************** */
      footerSection={null}
      /** **************************************
       * Style
       *************************************** */
      cssVars={{
        '--layout-nav-vertical-width': '300px',
        '--layout-dashboard-content-pt': theme.spacing(1),
        '--layout-dashboard-content-pb': theme.spacing(8),
        '--layout-dashboard-content-px': theme.spacing(5),
      }}
      sx={{
        [`& .${layoutClasses.hasSidebar}`]: {
          [theme.breakpoints.up(layoutQuery)]: {
            pl: 'var(--layout-nav-vertical-width)',
          },
        },
        ...sx,
      }}
    >
      <Main>{children}</Main>
    </LayoutSection>
  );
}
