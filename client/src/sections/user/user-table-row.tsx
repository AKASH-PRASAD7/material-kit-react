import { useState, useCallback } from 'react';

import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import Popover from '@mui/material/Popover';
import TableRow from '@mui/material/TableRow';
import Checkbox from '@mui/material/Checkbox';
import MenuList from '@mui/material/MenuList';
import TableCell from '@mui/material/TableCell';
import IconButton from '@mui/material/IconButton';
import MenuItem, { menuItemClasses } from '@mui/material/MenuItem';
import Modal from '@mui/material/Modal';
import Typography from '@mui/material/Typography';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import InputLabel from '@mui/material/InputLabel';
import Button from '@mui/material/Button';
import { Iconify } from 'src/components/iconify';
import axios from 'axios';
import Cookies from 'js-cookie';

// ----------------------------------------------------------------------

export type UserProps = {
  id: string;
  name: string;
  role: string;
  email: string;
};

type UserTableRowProps = {
  row: UserProps;
  selected: boolean;
  onSelectRow: () => void;
};

export function UserTableRow({ row, selected, onSelectRow }: UserTableRowProps) {
  const [openPopover, setOpenPopover] = useState<HTMLButtonElement | null>(null);
  const [openModal, setOpenModal] = useState(false);
  const [editRole, setEditRole] = useState(row.role);
  const handleOpenPopover = useCallback((event: React.MouseEvent<HTMLButtonElement>) => {
    setOpenPopover(event.currentTarget);
  }, []);

  const handleClosePopover = useCallback(() => {
    setOpenPopover(null);
  }, []);
  const handleOpenModal = () => {
    setEditRole(row.role);
    setOpenModal(true);
    handleClosePopover();
  };
  const handleCloseModal = () => {
    setOpenModal(false);
  };

  const handleSaveChanges = async () => {
    try {
      await axios.put(
        'http://localhost:9000/api/user/update',
        {
          id: +row.id,
          role: editRole,
        },
        {
          headers: {
            roles: `${Cookies.get('role')}`,
          },
        }
      );
    } catch (error) {
      console.error('Error saving user', error.message);
    }
    handleCloseModal();
  };

  return (
    <>
      <TableRow hover tabIndex={-1} role="checkbox" selected={selected}>
        <TableCell padding="checkbox">
          <Checkbox disableRipple checked={selected} onChange={onSelectRow} />
        </TableCell>

        <TableCell component="th" scope="row">
          <Box gap={2} display="flex" alignItems="center">
            <Avatar alt={row.name} src="/assets/images/avatar/avatar-4.webp" />
            {row.name}
          </Box>
        </TableCell>

        <TableCell>{row.email}</TableCell>
        <TableCell>{row.role}</TableCell>

        <TableCell align="right">
          <IconButton onClick={handleOpenPopover}>
            <Iconify icon="eva:more-vertical-fill" />
          </IconButton>
        </TableCell>
      </TableRow>

      <Popover
        open={!!openPopover}
        anchorEl={openPopover}
        onClose={handleClosePopover}
        anchorOrigin={{ vertical: 'top', horizontal: 'left' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
      >
        <MenuList
          disablePadding
          sx={{
            p: 0.5,
            gap: 0.5,
            width: 140,
            display: 'flex',
            flexDirection: 'column',
            [`& .${menuItemClasses.root}`]: {
              px: 1,
              gap: 2,
              borderRadius: 0.75,
              [`&.${menuItemClasses.selected}`]: { bgcolor: 'action.selected' },
            },
          }}
        >
          <MenuItem onClick={handleOpenModal}>
            <Iconify icon="solar:pen-bold" />
            Edit
          </MenuItem>
        </MenuList>
      </Popover>

      <Modal open={openModal} onClose={handleCloseModal} aria-labelledby="modal-title">
        <Box
          sx={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            width: 400,
            bgcolor: 'background.paper',
            borderRadius: 2,
            p: 4,
            boxShadow: 24,
          }}
        >
          <Typography id="modal-title" variant="h6" component="h2" gutterBottom>
            Edit Role for {row.name}
          </Typography>

          <FormControl fullWidth variant="outlined" sx={{ mb: 3 }}>
            <InputLabel id="role-select-label">Role</InputLabel>
            <Select
              labelId="role-select-label"
              value={editRole}
              onChange={(e) => setEditRole(e.target.value as string)}
              label="Role"
            >
              <MenuItem value="admin">admin</MenuItem>
              <MenuItem value="user">user</MenuItem>
              <MenuItem value="sales">sales</MenuItem>
              <MenuItem value="operations">operations</MenuItem>
            </Select>
          </FormControl>

          <Box display="flex" justifyContent="flex-end" gap={2}>
            <Button onClick={handleCloseModal} color="inherit">
              Cancel
            </Button>
            <Button onClick={handleSaveChanges} variant="contained">
              Save
            </Button>
          </Box>
        </Box>
      </Modal>
    </>
  );
}
