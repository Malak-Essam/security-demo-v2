package com.malak.demo.security.services;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.malak.demo.entities.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails{
	private final String username;
	private final String password;
	// Collection is an interface (think of it as a datatype) that can contain multiple objects.
	// It is the superinterface for List, Set, and other collection types.
	// In this case, we specify what *type* of objects this collection will hold using generics.
	// `? extends GrantedAuthority` means it can hold any object that is a subtype of GrantedAuthority,
	// such as SimpleGrantedAuthority or any custom implementation.
	private final Collection<? extends GrantedAuthority> authorities;
	
	public static UserDetailsImpl build(User user) {
		Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
		
		return new UserDetailsImpl(
                user.getUsername(),
                user.getPassword(),
                authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
}
