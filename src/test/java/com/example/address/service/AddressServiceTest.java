package com.example.address.service;

import com.example.address.TestUtils;
import com.example.address.entity.Address;
import com.example.address.exception.AddressNotFoundException;
import com.example.address.model.AddressModel;
import com.example.address.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.address.TestUtils.DEFAULT_ID;
import static com.example.address.TestUtils.getDefaultTestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressServiceTest {

    private AddressRepository addressRepository = mock(AddressRepository.class);

    private GeographicalCoordinatesService geoService = mock(GeographicalCoordinatesService.class);

    private AddressService addressService = new AddressService(addressRepository, geoService);

    @Test
    public void testSaveAddress() {
        AddressModel addressModel = TestUtils.getDefaultTestProduct();
        Address address = addressModel.toEntity();
        address.setId(DEFAULT_ID);

        when(addressRepository.save(Mockito.any(Address.class)))
                .thenReturn(address);
        when(geoService.findGeographicalCoordinates((Mockito.any(Address.class))))
                .thenReturn(address);

        Long id = addressService.saveAddress(addressModel);

        assertNotNull(id);
        assertEquals(DEFAULT_ID, id);
    }

    @Test
    public void testUpdateAddress() {
        AddressModel addressModel = TestUtils.getDefaultTestProduct();
        Address address = addressModel.toEntity();
        address.setId(DEFAULT_ID);

        when(addressRepository.findById(DEFAULT_ID))
                .thenReturn(Optional.of(address));
        when(addressRepository.save(Mockito.any(Address.class)))
                .thenReturn(address);
        when(geoService.findGeographicalCoordinates((Mockito.any(Address.class))))
                .thenReturn(address);

        Long id = addressService.updateOrCreateAddress(DEFAULT_ID, addressModel);

        assertNotNull(id);
        assertEquals(DEFAULT_ID, id);
    }

    @Test
    public void testUpdateCreateAddress() {
        AddressModel addressModel = TestUtils.getDefaultTestProduct();
        Address address = addressModel.toEntity();
        address.setId(DEFAULT_ID);

        when(addressRepository.findById(DEFAULT_ID))
                .thenReturn(Optional.empty());
        when(addressRepository.save(Mockito.any(Address.class)))
                .thenReturn(address);
        when(geoService.findGeographicalCoordinates((Mockito.any(Address.class))))
                .thenReturn(address);

        Long id = addressService.updateOrCreateAddress(DEFAULT_ID, addressModel);

        assertNotNull(id);
        assertEquals(DEFAULT_ID, id);
    }

    @Test
    public void testDeleteProduct() throws AddressNotFoundException {
        Address address = TestUtils.getDefaultTestProduct().toEntity();

        when(addressRepository.findById(DEFAULT_ID))
                .thenReturn(Optional.of(address));

        addressService.deleteAddress(DEFAULT_ID);

        Mockito.verify(addressRepository).delete(address);
    }

    @Test
    public void testDeleteNoExistingAddress() {
        when(addressRepository.findById(DEFAULT_ID))
                .thenReturn(Optional.empty());

        try {
            addressService.deleteAddress(DEFAULT_ID);
        } catch (AddressNotFoundException e) {
            assertThat(e.getMessage()).isNotNull();
        }
    }

    @Test
    public void testListAllNullList() {
        when(addressRepository.findAll())
                .thenReturn(null);

        List<Address> addressModels = addressService.getAllAddresses();

        assertNull(addressModels);
    }

    @Test
    public void testListAllEmptyList() {
        when(addressRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<Address> addressModels = addressService.getAllAddresses();

        assertNotNull(addressModels);
    }

    @Test
    public void testListAll() {
        List<Address> addressModelList = new ArrayList<>();
        Address address = getDefaultTestProduct().toEntity();
        addressModelList.add(address);

        when(addressRepository.findAll())
                .thenReturn(addressModelList);

        List<Address> addressModels = addressService.getAllAddresses();

        assertThat(addressModels)
                .isNotNull()
                .isNotEmpty();
        assertEquals(1, addressModels.size());
        assertEquals(address, addressModelList.get(0));
    }

    @Test
    public void testFindById() throws AddressNotFoundException {
        AddressModel addressModel = getDefaultTestProduct();
        Optional<Address> optionalProduct = Optional.of(addressModel.toEntity());

        when(addressRepository.findById(DEFAULT_ID))
                .thenReturn(optionalProduct);

        AddressModel foundAddressModel = addressService.getAddressById(DEFAULT_ID);

        assertEquals(addressModel, foundAddressModel);
    }

    @Test
    public void testFindByNotExistingId() {
        when(addressRepository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class,
                () -> addressService.getAddressById(DEFAULT_ID));
    }
}
